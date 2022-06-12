package com.team1.tm.bantutani.app.controller;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.NewsDTO;
import com.team1.tm.bantutani.app.dto.TagsDTO;
import com.team1.tm.bantutani.app.dto.response.NewsResponseDTO;
import com.team1.tm.bantutani.app.dto.response.NewsResponseMinDTO;
import com.team1.tm.bantutani.app.dto.response.NewsResponseMinDTOAll;
import com.team1.tm.bantutani.app.dto.response.StringResponse;
import com.team1.tm.bantutani.app.service.NewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class NewsController {
    private NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping(value = "/public/news/v1/media/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get News Image", description = "get news image")
    public byte[] getMedia(@PathVariable String name) {
        return newsService.getMedia(name);
    }

    @GetMapping("/public/news/v1/data/{start}/{end}/get")
    @Tag(name = "Get News Between 2 Date", description = "get news data between 2 dates with pagination")
    public NewsResponseMinDTOAll getAllNews(@PathVariable Date start,
                                            @PathVariable Date end,
                                            @RequestParam int page,
                                            @RequestParam int size) {
        return newsService.getAllNews(start, end, page, size);
    }

    @GetMapping("/public/news/v1/data/{mount}/search")
    @Tag(name = "News Search", description = "search suggestion for news with title based response and mount of data to displayed (static)")
    public List<String> search(@RequestParam(value = "q") String quest,
                                @PathVariable int mount) {
        return newsService.getSearchTitles(quest, mount);
    }

    @GetMapping("/public/news/v1/search/get")
    @Tag(name = "News Response Data Search", description = "get data from user search")
    public List<NewsResponseMinDTO> getDataSearch(@RequestParam(value = "q") String quest,
                                        @RequestParam int page, @RequestParam int size) {
        return newsService.getNews(quest, page, size);
    }

    @GetMapping("/public/news/v1/data/get")
    @Tag(name = "News Response Data", description = "get detailed data from user")
    public NewsResponseDTO getData(@RequestParam Long id) {
        return newsService.getNews(id);
    }

    @GetMapping("/news/v1/search/tags/get")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Get Tags", description = "get tags data suggestion with specific size data that displayed")
    public List<TagsDTO> getTagsData(@RequestParam String name, @RequestParam int size) {
        return newsService.getTags(name, size);
    }

    @GetMapping("/news/v1/unused/tags/get")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Get Unused Tags", description = "get all tags data that unused")
    public List<TagsDTO> getTagsData() {
        return newsService.getUnusedTags();
    }

    @PostMapping("/news/v1/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Add News", description = "adding new news data")
    public StringResponse addNews(@ModelAttribute NewsDTO newsDTO) {
        newsService.addNews(newsDTO, newsDTO.getKeywordsRequest());
        return new StringResponse.Builder().status("success").message("Success add new news").build();
    }

    @PostMapping(value = "/news/v1/add/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Add News Image", description = "adding new news image (not replace to another images)")
    public StringResponse addImageNews(@RequestParam(value = "image") MultipartFile image,
                                @RequestParam Long newsId) {
        newsService.updateImage(image,null, newsId, false);
        return new StringResponse.Builder().status("success").message("Success add new image to news id "+newsId).build();
    }

    @PutMapping("/news/v1/update/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Modify News", description = "modify news data")
    public StringResponse updateNews(@ModelAttribute NewsDTO newsDTO) {
        newsService.updateNews(newsDTO, newsDTO.getKeywordsRequest());
        return new StringResponse.Builder().status("success").message("Success update news with news id "+newsDTO.getId()).build();
    }

    @DeleteMapping("/news/v1/delete/image/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete News Image", description = "delete news image")
    public StringResponse deleteImageNews(@RequestParam Long newsId, @PathVariable String name) {
        newsService.updateImage(null, name, newsId, true);
        return new StringResponse.Builder().status("success").message("Success delete news image with news id "+newsId).build();
    }

    @DeleteMapping("/news/v1/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete News", description = "delete news data")
    public StringResponse deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return new StringResponse.Builder().status("success").message("Success delete news with id "+id).build();
    }
    
    @DeleteMapping("/news/v1/delete/tag/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Tag", description = "delete tags data")
    public StringResponse deleteTag(@PathVariable Long id) {
        newsService.deleteTags(id);
        return new StringResponse.Builder().status("success").message("Success delete tag with id "+id).build();
    }
}
