package com.ves.models;

public class ResizeResource implements IResource {

    private final int widthPercentage;
    private final int heightPercentage;
    
    public ResizeResource( int widthPercentage, int heightPercentage ) {
        this.widthPercentage = widthPercentage;
        this.heightPercentage = heightPercentage;
    }
    
    @Override
    public ResourceType getType() {
        return ResourceType.RESIZE;
    }

    @Override
    public String getValue() {
        return widthPercentage + ";" + heightPercentage;
    }
    
    @Override
    public ResizeResource clone() {
        return new ResizeResource( widthPercentage, heightPercentage);
    }
    
    @Override
    public Boolean isUnique() {
        return true;
    }
}
