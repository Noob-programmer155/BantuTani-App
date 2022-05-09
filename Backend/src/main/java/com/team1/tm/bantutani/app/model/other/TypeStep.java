package com.team1.tm.bantutani.app.model.other;

public enum TypeStep {
    TIPS("Tips"),
    TRICK("Trik");
    private String label;

    TypeStep(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
