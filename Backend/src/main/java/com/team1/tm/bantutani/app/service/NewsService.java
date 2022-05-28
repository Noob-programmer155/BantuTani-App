package com.team1.tm.bantutani.app.service;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.NewsDTO;
import com.team1.tm.bantutani.app.dto.TagsDTO;
import com.team1.tm.bantutani.app.dto.response.NewsResponseDTO;
import com.team1.tm.bantutani.app.dto.response.NewsResponseMinDTO;
import com.team1.tm.bantutani.app.model.News;
import com.team1.tm.bantutani.app.model.NewsTags;
import com.team1.tm.bantutani.app.repository.NewsRepo;
import com.team1.tm.bantutani.app.repository.NewsTagsRepo;
import com.team1.tm.bantutani.app.repository.UserRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private NewsRepo newsRepo;
    private UserRepo userRepo;
    private StorageConfig storageConfig;
    private NewsTagsRepo newsTagsRepo;

    public NewsService(NewsRepo newsRepo, UserRepo userRepo, StorageConfig storageConfig, NewsTagsRepo newsTagsRepo) {
        this.newsRepo = newsRepo;
        this.userRepo = userRepo;
        this.storageConfig = storageConfig;
        this.newsTagsRepo = newsTagsRepo;
    }

    @Cacheable(value = "allMediaNews", key = "#name")
    public byte[] getMedia (String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.NEWS);
    }

    @Cacheable(value = "allNews", key = "{#start,#end}")
    public List<NewsResponseMinDTO> getAllNews (Date start, Date end,int page, int size) {
        return newsRepo.getAllNews(start, end, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date")))
                .map(item ->
                new NewsResponseMinDTO.Builder().
                        id(item.getId()).
                        title(item.getTitle()).
                        images((!item.getImages().isEmpty())?item.getImages().get(0):null).
                        video(item.getVideo()).
                        date(item.getDates()).
                        build()).getContent();
    }

    @Cacheable(value = "searchTitle", key = "#title")
    public List<String> getSearchTitles(String title, int size) {
        return newsRepo.findAllDistinctByTitleContainingOrKeywordsName(title, title, PageRequest.of(0,size)).map(item -> item.getTitle()).getContent();
    }

    @Cacheable(value = "newsCache")
    public List<NewsResponseMinDTO> getNews(String title, int page, int size) {
        return newsRepo.findDistinctByTitleContainingOrKeywordsName(title, title,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"title"))).map(item ->
                new NewsResponseMinDTO.Builder().
                        id(item.getId()).
                        title(item.getTitle()).
                        images((!item.getImages().isEmpty())?item.getImages().get(0):null).
                        video(item.getVideo()).
                        date(item.getDates()).
                        build()).getContent();
    }

    @Cacheable(value = "mainNewsCache")
    public NewsResponseDTO getNews(Long id) {
        return newsRepo.findById(id).map(item ->
                new NewsResponseDTO.Builder().id(item.getId()).description(item.getDescriptions()).
                        descriptionSummary(item.getDescriptionSummary()).source(item.getSources()).
                        date(item.getDates()).images(item.getImages()).
                        keywords(item.getKeywords().stream().map(it -> it.getName()).collect(Collectors.toList())).
                        title(item.getTitle()).video(item.getVideo()).build()).get();
    }

    @Cacheable(value = "tagsCache", key = "#name")
    public List<TagsDTO> getTags(String name, int size) {
        return newsTagsRepo.findByNameContaining(name,PageRequest.of(0,size)).map(item -> {
            TagsDTO tagsDTO =  new TagsDTO();
            tagsDTO.setId(item.getId());
            tagsDTO.setName(item.getName());
            return tagsDTO;
        }).getContent();
    }

    @Transactional
    public void addNews(NewsDTO newsDTO, List<String> newTags) {
        News news = new News.Builder().date(new Date(new java.util.Date().getTime())).
                title(newsDTO.getTitle()).
                description(newsDTO.getDescription()).
                descriptionSummary(newsDTO.getDescriptionSummary()).
                source(newsDTO.getSource()).build();
        if (newsDTO.getImages() != null && !newsDTO.getImages().isEmpty()) {
            List<String> images = newsDTO.getImages().stream().map(item -> {
                return storageConfig.addMedia(item,"newsImages", StorageConfig.SubDir.NEWS);
            }).collect(Collectors.toList());
            news.setImages(images);
        }
        if (newTags != null && !newTags.isEmpty()) {
            newTags.stream().forEach(item -> {
                if (!newsTagsRepo.existsByName(item)) {
                    NewsTags newsTags = new NewsTags();
                    newsTags.setName(item);
                    NewsTags newsTags1 = newsTagsRepo.save(newsTags);
                    newsTags1.addNews(news);
                } else {
                    NewsTags tag = newsTagsRepo.findByName(item).get();
                    tag.addNews(news);
                }
            });
        }
        if (newsDTO.getKeywords() != null && !newsDTO.getKeywords().isEmpty()) {
            newsDTO.getKeywords().forEach(item -> {
                NewsTags tag = newsTagsRepo.findById(item).get();
                tag.addNews(news);
            });
        }
        if (newsDTO.getVideo() != null && !newsDTO.getVideo().isEmpty()) {
            news.setVideo(newsDTO.getVideo());
        }
        newsRepo.save(news);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "mainNewsCache", key = "#newsDTO.getId", condition = "#newsDTO.getId!=null"),
            @CacheEvict(value = {"searchTitle"}, allEntries = true),
            @CacheEvict(value = {"newsCache","allNews","tagsCache"},allEntries = true)
    })
    public void updateNews(NewsDTO newsDTO, List<String> newTags) {
        News news = newsRepo.findById(newsDTO.getId()).get();
        if (newsDTO.getTitle() != null && !newsDTO.getTitle().isBlank())
            news.setTitle(newsDTO.getTitle());
        if (newsDTO.getDescription() != null && !newsDTO.getDescription().isBlank())
            news.setDescriptions(newsDTO.getDescription());
        if (newsDTO.getDescriptionSummary() != null && !newsDTO.getDescriptionSummary().isBlank())
            news.setDescriptionSummary(newsDTO.getDescriptionSummary());
        if (newsDTO.getKeywords() != null && !newsDTO.getKeywords().isEmpty()) {
            new LinkedHashSet<>(news.getKeywords()).forEach(item -> {
                item.removeNews(news);
            });
            newsDTO.getKeywords().forEach(item -> {
                NewsTags tag = newsTagsRepo.findById(item).get();
                tag.addNews(news);
            });
        }
        if (newTags != null && !newTags.isEmpty()) {
            newTags.stream().forEach(item -> {
                if (!newsTagsRepo.existsByName(item)) {
                    NewsTags newsTags = new NewsTags();
                    newsTags.setName(item);
                    NewsTags newsTags1 = newsTagsRepo.save(newsTags);
                    newsTags1.addNews(news);
                } else {
                    NewsTags tag = newsTagsRepo.findByName(item).get();
                    tag.addNews(news);
                }
            });
        }
        if (newsDTO.getVideo() != null && !newsDTO.getVideo().isEmpty()) {
            news.setVideo(newsDTO.getVideo());
        }
        newsRepo.save(news);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "mainNewsCache", key = "#id", condition = "#id!=null"),
            @CacheEvict(value = {"allNews","newsCache"},allEntries = true)
    })
    public void updateImage(MultipartFile image, String imgName, Long id, boolean delete) {
        News news = newsRepo.findById(id).get();
        if (delete) {
            deleteImage(imgName, news);
        } else {
//            news.getImages().add("example"+ UUID.randomUUID().toString()+".jpg");
            news.getImages().add(storageConfig.addMedia(image, "newsImages", StorageConfig.SubDir.NEWS));
        }
        newsRepo.save(news);
    }

    @CacheEvict(value = "allMediaNews", key = "#imgName", condition = "#imgName!=null")
    public void deleteImage(String imgName, News news){
        if (storageConfig.deleteMedia(imgName, StorageConfig.SubDir.NEWS))
            news.getImages().remove(imgName);
    }

    @Transactional
    @CacheEvict(value = {"tagsCache","mainNewsCache"}, allEntries = true)
    public void deleteTags(Long id) {
        newsTagsRepo.deleteById(id);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "mainNewsCache", key = "#id", condition = "#id!=null"),
            @CacheEvict(value = {"allNews","newsCache","searchTitle","tagsCache"},allEntries = true)
    })
    public void deleteNews(Long id) {
        News news = newsRepo.findById(id).get();
        news.getImages().forEach(item -> {
            deleteImage(item);
        });
        new LinkedHashSet<>(news.getKeywords()).forEach(item -> {
            item.removeNews(news);
        });
        News news1 = newsRepo.save(news);
        newsRepo.delete(news1);
    }

    @CacheEvict(value = "allMediaNews", key = "#imgName", condition = "#imgName!=null")
    public void deleteImage(String imgName){
        if (!storageConfig.deleteMedia(imgName, StorageConfig.SubDir.NEWS))
            throw new RuntimeException("failed delete news, something wrong with file images, please try again");
    }
}
