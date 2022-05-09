package com.team1.tm.bantutani.app.model.other;

public enum TypeActivity {
    PLANTING("Penanaman"),
    CARE("Perawatan"),
    DISEASE_TREATMENT("Pengobatan Penyakit"),
    WEEDS_HANDLING("Penanganan Gulma"),
    PEST_HANDLING("Penanganan Hama"),
    PRESOLD("Sebelum Penjualan"),
    ONSALE("Penjualan");

    private String label;

    TypeActivity(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
