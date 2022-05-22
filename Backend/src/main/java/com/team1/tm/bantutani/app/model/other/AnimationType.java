package com.team1.tm.bantutani.app.model.other;

public enum AnimationType {
    TIPSNTRICK(0),
    PLANTSCARE(1),
    PLANTSPLANTING(2);

    private int id;
    AnimationType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AnimationType getFromId(int id) {
        for (AnimationType e:values())
            if(e.getId() == id)
                return e;
        throw new IllegalArgumentException(""+id);
    }
}
