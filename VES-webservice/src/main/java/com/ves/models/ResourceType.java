package com.ves.models;

public enum ResourceType {
    UNDEFINED(0),
    VIDEO(1),
    RESIZE(2),
    SUBTITLE(3);
    
    private final int value;
        
    private ResourceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
