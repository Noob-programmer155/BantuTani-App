package com.team1.tm.bantutani.app.dto.response;

import java.util.List;

public class NewsResponseMinDTOAll {
    private List<NewsResponseMinDTO> data;

    public NewsResponseMinDTOAll() {
    }

    public NewsResponseMinDTOAll(List<NewsResponseMinDTO> data) {
        this.data = data;
    }

    public List<NewsResponseMinDTO> getData() {
        return data;
    }

    public void setData(List<NewsResponseMinDTO> data) {
        this.data = data;
    }
}
