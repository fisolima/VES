package com.ves.Models;

public abstract class FileResource implements IResource {
    
    protected String fileName;
    
    public FileResource( String fileName )
    {
        this.fileName = fileName;
    }
    
    @Override
    public ResourceType getType() {
        return ResourceType.UNDEFINED;
    }

    @Override
    public String getValue() {
        return fileName;
    }

    @Override
    public IResource clone() {
        throw new UnsupportedOperationException( "Not implemented");
    }
    
}
