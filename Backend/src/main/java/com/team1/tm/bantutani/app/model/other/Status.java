package com.team1.tm.bantutani.app.model.other;

public enum Status {
    EXPERTS("Tenaga Ahli"),
    FARMER("Petani"),
    USER("Pelanggan"),
    ADMIN("Administrator"),
    DISTRIBUTOR("Distributor"),
    SALES("Penjual");

    private String label;
    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
