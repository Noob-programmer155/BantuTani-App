package com.team1.tm.bantutani.app.dto.response;

import java.util.List;

public class PlantsResponseMinDTOAll {
    private List<PlantsResponseMinDTO> data;

    public PlantsResponseMinDTOAll() {
    }

    public PlantsResponseMinDTOAll(List<PlantsResponseMinDTO> data) {
        this.data = data;
    }

    public List<PlantsResponseMinDTO> getData() {
        return data;
    }

    public void setData(List<PlantsResponseMinDTO> data) {
        this.data = data;
    }
}


