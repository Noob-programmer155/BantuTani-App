package com.team1.tm.bantutani.app.controller;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.NewsDTO;
import com.team1.tm.bantutani.app.dto.TagsDTO;
import com.team1.tm.bantutani.app.service.NewsService;
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
    @ResponseBody
    private byte[] getMedia(@PathVariable String name) {
        return newsService.getMedia(name);
    }

    @GetMapping("/news/v1/{start}/{end}/get")
    @ResponseBody
    private List<NewsDTO> getAllNews(@PathVariable Date start,
                                     @PathVariable Date end,
                                     @RequestParam int page,
                                     @RequestParam int size) {
        return newsService.getAllNews(start, end, page, size);
    }

    @GetMapping("/news/v1/{mount}/search")
    @ResponseBody
    private List<String> search(@RequestParam(value = "q") String quest,
                                @PathVariable int mount) {
        return newsService.getSearchTitles(quest, mount);
    }

    @GetMapping("/news/v1/search/get")
    @ResponseBody
    private List<NewsDTO> getDataSearch(@RequestParam(value = "q") String quest,
                                        @RequestParam int page, @RequestParam int size) {
        return newsService.getNews(quest, page, size);
    }

    @GetMapping("/news/v1/search/tags/get")
    @ResponseBody
    private List<TagsDTO> getTagsData(@RequestParam String name) {
        return newsService.getTags(name);
    }

    @PostMapping("/news/v1/add")
    private String addNews(@ModelAttribute NewsDTO newsDTO) {
        newsService.addNews(newsDTO, newsDTO.getKeywordsRequest());
        return "success";
    }

    @PostMapping("/news/v1/add/image")
    private String addImageNews(@ModelAttribute MultipartFile image,
                                @RequestParam Long newsId) {
        newsService.updateImage(image,null, newsId, false);
        return "success";
    }

    @PutMapping("/news/v1/update/all")
    private String updateNews(@ModelAttribute NewsDTO newsDTO) {
        newsService.updateNews(newsDTO, newsDTO.getKeywordsRequest());
        return "success";
    }

    @DeleteMapping("/news/v1/delete/image/{name}")
    private String deleteImageNews(@RequestParam Long newsId,
                              @PathVariable String name) {
        newsService.updateImage(null, name, newsId, true);
        return "success";
    }

    @DeleteMapping("/news/v1/delete/{id}")
    private String deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "success";
    }
}
