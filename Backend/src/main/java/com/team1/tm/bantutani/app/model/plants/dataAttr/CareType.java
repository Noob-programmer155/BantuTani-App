package com.team1.tm.bantutani.app.model.plants.dataAttr;

public enum CareType {
    GENERAL("Umum"),
    CAREDISEASE("Penyakit"),
    CAREWEEDS("Gulma"),
    CAREPEST("Hama");

    private String label;
    CareType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
