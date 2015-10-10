package com.ves.Models;


public interface IResource {
    
    public ResourceType getType();
    
    public String getValue();
    
    public IResource clone();
}
