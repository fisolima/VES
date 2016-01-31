package com.ves.models;

public class FinalVideoResource extends FileResource {

    public FinalVideoResource(String fileName) {
        super(fileName);
    }

    @Override
    public ResourceType getType() {
        return ResourceType.FINALVIDEO;
    }

    @Override
    public IResource clone() {
        return new FinalVideoResource( fileName );
    }
 
    @Override
    public Boolean isUnique() {
        return true;
    }
}