package com.team1.tm.bantutani.app.dto.response;

import java.util.List;

public class CommodityResponseDTOAll {
    private List<CommodityResponseDTO> data;

    public CommodityResponseDTOAll() {
    }

    public CommodityResponseDTOAll(List<CommodityResponseDTO> data) {
        this.data = data;
    }

    public List<CommodityResponseDTO> getData() {
        return data;
    }

    public void setData(List<CommodityResponseDTO> data) {
        this.data = data;
    }
}
