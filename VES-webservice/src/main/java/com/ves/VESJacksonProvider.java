package com.ves;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class VESJacksonProvider implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
      MAPPER.setSerializationInclusion(Include.NON_EMPTY);
      MAPPER.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
    }

    public VESJacksonProvider() {
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return MAPPER;
    } 
}
