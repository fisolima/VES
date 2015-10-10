package com.ves.models;


public interface IResource {
    
    public ResourceType getType();
    
    public String getValue();
    
    public IResource clone();
}
