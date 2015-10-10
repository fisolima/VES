package com.ves.Models;

public class SubtitlesResource extends FileResource {

    public SubtitlesResource(String fileName) {
        super(fileName);
    }

    @Override
    public ResourceType getType() {
        return ResourceType.SUBTITLE;
    }

    @Override
    public IResource clone() {
        return new SubtitlesResource( fileName );
    }
    
}
