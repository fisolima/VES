package com.ves.models;

public class VideoResource extends FileResource {

    public VideoResource(String fileName) {
        super(fileName);
    }

    @Override
    public ResourceType getType() {
        return ResourceType.VIDEO;
    }

    @Override
    public IResource clone() {
        return new VideoResource( fileName );
    }
 
    @Override
    public Boolean isUnique() {
        return true;
    }
}
