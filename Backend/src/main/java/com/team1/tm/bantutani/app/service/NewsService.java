package com.team1.tm.bantutani.app.service;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.NewsDTO;
import com.team1.tm.bantutani.app.dto.TagsDTO;
import com.team1.tm.bantutani.app.model.News;
import com.team1.tm.bantutani.app.model.NewsTags;
import com.team1.tm.bantutani.app.repository.NewsRepo;
import com.team1.tm.bantutani.app.repository.NewsTagsRepo;
import com.team1.tm.bantutani.app.repository.UserRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "allMediaNews")
    public byte[] getMedia (String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.NEWS);
    }

    @Cacheable(value = "allNews")
    public List<NewsDTO> getAllNews (Date start, Date end,int page, int size) {
        return newsRepo.getAllNews(start, end, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date")))
                .stream().map(item ->
                new NewsDTO.Builder().
                        id(item.getId()).
                        title(item.getTitle()).
                        imagesResponse(item.getImages()).
                        keywordsResponse(item.getKeywords().stream().map(it -> it.getName()).collect(Collectors.toList())).
                        video(item.getVideo()).
                        date(item.getDate()).
                        description(item.getDescription()).
                        descriptionSummary(item.getDescriptionSummary()).
                        source(item.getSource()).build()).collect(Collectors.toList());
    }

    @Cacheable(value = "searchTitle")
    public List<String> getSearchTitles(String title, int size) {
        return newsRepo.findAllDistinctByTitleContainingOrKeywordsName(title, title, PageRequest.of(0,size)).map(item -> item.getTitle()).getContent();
    }

    @Cacheable(value = "newsCache")
    public List<NewsDTO> getNews(String title, int page, int size) {
        return newsRepo.findDistinctByTitleContainingOrKeywordsName(title, title,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"title"))).map(item ->
                new NewsDTO.Builder().
                        id(item.getId()).
                        title(item.getTitle()).
                        imagesResponse(item.getImages()).
                        keywordsResponse(item.getKeywords().stream().map(it -> it.getName()).collect(Collectors.toList())).
                        video(item.getVideo()).
                        date(item.getDate()).
                        description(item.getDescription()).
                        descriptionSummary(item.getDescriptionSummary()).
                        source(item.getSource()).build()).getContent();
    }

    @Cacheable(value = "tagsCache")
    public List<TagsDTO> getTags(String name) {
        return newsTagsRepo.findByNameContaining(name,PageRequest.of(0,10)).map(item -> {
            TagsDTO tagsDTO =  new TagsDTO();
            tagsDTO.setId(item.getId());
            tagsDTO.setName(item.getName());
            return tagsDTO;
        }).getContent();
    }

    @Transactional
    @CacheEvict(value = {"allNews","newsCache","searchTitle","tagsCache"},allEntries = true)
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
    @CacheEvict(value = {"allNews","newsCache","searchTitle","tagsCache"},allEntries = true)
    public void updateNews(NewsDTO newsDTO, List<String> newTags) {
        News news = newsRepo.findById(newsDTO.getId()).get();
        if (newsDTO.getTitle() != null && !newsDTO.getTitle().isBlank())
            news.setTitle(newsDTO.getTitle());
        if (newsDTO.getDescription() != null && !newsDTO.getDescription().isBlank())
            news.setDescription(newsDTO.getDescription());
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
    @CacheEvict(value = {"allNews","newsCache","searchTitle","allMediaNews"},allEntries = true)
    public void updateImage(MultipartFile image, String imgName, Long id, boolean delete) {
        News news = newsRepo.findById(id).get();
        if (delete) {
            if (storageConfig.deleteMedia(imgName, StorageConfig.SubDir.NEWS))
                news.getImages().remove(imgName);
        } else {
//            news.getImages().add("example"+ UUID.randomUUID().toString()+".jpg");
            news.getImages().add(storageConfig.addMedia(image, "newsImages", StorageConfig.SubDir.NEWS));
        }
        newsRepo.save(news);
    }

    @Transactional
    @CacheEvict(value = {"allNews","newsCache","searchTitle","tagsCache","allMediaNews"},allEntries = true)
    public void deleteNews(Long id) {
        News news = newsRepo.findById(id).get();
        news.getImages().forEach(item -> {
            if (!storageConfig.deleteMedia(item, StorageConfig.SubDir.NEWS))
                throw new RuntimeException("failed delete news, something wrong with file images, please try again");
        });
        new LinkedHashSet<>(news.getKeywords()).forEach(item -> {
            item.removeNews(news);
        });
        News news1 = newsRepo.save(news);
        newsRepo.delete(news1);
    }
}
