package com.team1.tm.bantutani.app.model.other;

import org.springframework.security.core.GrantedAuthority;

public enum Status implements GrantedAuthority {
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

    public static Status getFromLabel(String label) {
        for (Status e : values())
            if (e.getLabel().equalsIgnoreCase(label))
                return e;
        throw new IllegalArgumentException(label);
    }

    @Override
    public String getAuthority() {
        return toString();
    }
}
