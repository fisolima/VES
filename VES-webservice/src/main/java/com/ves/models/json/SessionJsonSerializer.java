package com.ves.models.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ves.models.IResource;
import com.ves.models.ResizeResource;
import com.ves.models.ResourceType;
import com.ves.models.Session;
import com.ves.models.SubtitlesResource;
import com.ves.models.VideoResource;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class SessionJsonSerializer implements JsonSerializer<Session>, JsonDeserializer<Session> {

    @Override
    public JsonElement serialize(Session t, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.add("id", new JsonPrimitive(t.getId()));
        jsonObject.add("status", new JsonPrimitive(t.getStatus().getValue()));
        jsonObject.add("progress", new JsonPrimitive(t.getProgress()));
        jsonObject.add("progressDescription", new JsonPrimitive(t.getProgressDescription()));
        jsonObject.add("progressLastModify", new JsonPrimitive(t.getProgressLastModify().getTime()));
        
        JsonArray jsonResources = new JsonArray();
        
        Collection<IResource> resources = t.getResources();
        
        Iterator<IResource> resourceIterator = resources.iterator();
        
        while (resourceIterator.hasNext()) {
            IResource currentRes = resourceIterator.next();
            
            JsonObject jsonObjectResource = new JsonObject();
            
            jsonObjectResource.add("type", new JsonPrimitive(currentRes.getType().getValue()));
            
            switch (currentRes.getType())
            {
                case SUBTITLE:
                case VIDEO:
                    jsonObjectResource.add("file", new JsonPrimitive(currentRes.getValue()));
                    break;
                    
                case RESIZE:
                    jsonObjectResource.add("w", new JsonPrimitive( ((ResizeResource)currentRes).getWidthPercentage() ));
                    jsonObjectResource.add("h", new JsonPrimitive( ((ResizeResource)currentRes).getHeightPercentage() ));
                    break;
            }
            
            jsonResources.add(jsonObjectResource);
        }
        
        jsonObject.add("resources", jsonResources);
        
        return jsonObject;
    }

    @Override
    public Session deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();
        
        String id = jsonObject.get("id").getAsString();
        Session.Status status = Session.Status.values()[jsonObject.get("status").getAsInt()];
        int progress = jsonObject.get("progress").getAsInt();
        String progressDescription = jsonObject.get("progressDescription").getAsString();
        Date progressLastModify = new Date(jsonObject.get("progressLastModify").getAsLong());

        Session session = new Session(id);
        
        session.setStatus(status);
        session.setProgress(progress);
        session.setProgressDescription(progressDescription);
        session.setProgressLastModify(progressLastModify);
        
        JsonArray jsonResourceArray = jsonObject.get("resources").getAsJsonArray();
        
        for (int i = 0; i < jsonResourceArray.size(); i++)
        {
            JsonObject jsonResource = jsonResourceArray.get(i).getAsJsonObject();
          
            ResourceType rType = ResourceType.values()[jsonResource.get("type").getAsInt()];
            
            switch (rType)
            {
                case SUBTITLE:
                    session.addResource(new SubtitlesResource(jsonResource.get("file").getAsString()));
                    break;
                case VIDEO:
                    session.addResource(new VideoResource(jsonResource.get("file").getAsString()));
                    break;
                case RESIZE:
                    session.addResource(new ResizeResource(jsonResource.get("w").getAsInt(),jsonResource.get("h").getAsInt()));
                    break;
            }
        }

        return session;
    }
    
}
