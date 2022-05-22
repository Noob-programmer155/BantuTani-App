package com.team1.tm.bantutani.app.controller;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.NewsDTO;
import com.team1.tm.bantutani.app.dto.TagsDTO;
import com.team1.tm.bantutani.app.dto.response.NewsResponseDTO;
import com.team1.tm.bantutani.app.dto.response.NewsResponseMinDTO;
import com.team1.tm.bantutani.app.service.NewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
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

    @GetMapping(value = "/news/v1/media/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get News Image", description = "get news image")
    @ResponseBody
    private byte[] getMedia(@PathVariable String name) {
        return newsService.getMedia(name);
    }

    @GetMapping("/news/v1/{start}/{end}/get")
    @Tag(name = "Get News Between 2 Date", description = "get news data between 2 dates with pagination")
    @ResponseBody
    private List<NewsResponseMinDTO> getAllNews(@PathVariable Date start,
                                                @PathVariable Date end,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        return newsService.getAllNews(start, end, page, size);
    }

    @GetMapping("/news/v1/{mount}/search")
    @Tag(name = "News Search", description = "search suggestion for news with title based response and mount of data to displayed")
    @ResponseBody
    private List<String> search(@RequestParam(value = "q") String quest,
                                @PathVariable int mount) {
        return newsService.getSearchTitles(quest, mount);
    }

    @GetMapping("/news/v1/search/get")
    @Tag(name = "News Response Data Search", description = "get data from user search")
    @ResponseBody
    private List<NewsResponseMinDTO> getDataSearch(@RequestParam(value = "q") String quest,
                                        @RequestParam int page, @RequestParam int size) {
        return newsService.getNews(quest, page, size);
    }

    @GetMapping("/news/v1/get")
    @Tag(name = "News Response Data", description = "get detailed data from user")
    @ResponseBody
    private NewsResponseDTO getData(@RequestParam Long id) {
        return newsService.getNews(id);
    }

    @GetMapping("/news/v1/search/tags/get")
    @Tag(name = "Get Tags", description = "get tags data suggestion with specific size data that displayed")
    @ResponseBody
    private List<TagsDTO> getTagsData(@RequestParam String name, @RequestParam int size) {
        return newsService.getTags(name, size);
    }

    @PostMapping("/news/v1/add")
    @Tag(name = "Add News", description = "adding new news data")
    private String addNews(@ModelAttribute NewsDTO newsDTO) {
        newsService.addNews(newsDTO, newsDTO.getKeywordsRequest());
        return "success";
    }

    @PostMapping("/news/v1/add/image")
    @Tag(name = "Add News Image", description = "adding new news image (not replace to another images)")
    private String addImageNews(@ModelAttribute MultipartFile image,
                                @RequestParam Long newsId) {
        newsService.updateImage(image,null, newsId, false);
        return "success";
    }

    @PutMapping("/news/v1/update/all")
    @Tag(name = "Modify News", description = "modify news data")
    private String updateNews(@ModelAttribute NewsDTO newsDTO) {
        newsService.updateNews(newsDTO, newsDTO.getKeywordsRequest());
        return "success";
    }

    @DeleteMapping("/news/v1/delete/image/{name}")
    @Tag(name = "Delete News Image", description = "delete news image")
    private String deleteImageNews(@RequestParam Long newsId,
                              @PathVariable String name) {
        newsService.updateImage(null, name, newsId, true);
        return "success";
    }

    @DeleteMapping("/news/v1/delete/{id}")
    @Tag(name = "Delete News", description = "delete news data")
    private String deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "success";
    }
    
    @DeleteMapping("/news/v1/delete/tag/{id}")
    @Tag(name = "Delete Tag", description = "delete tags data")
    private String deleteTag(@PathVariable Long id) {
        newsService.deleteTags(id);
        return "success";
    }
}
