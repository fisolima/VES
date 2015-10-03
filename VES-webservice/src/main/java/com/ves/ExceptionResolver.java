
package com.ves;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionResolver implements ExceptionMapper<Exception>{
    
    @Override
    public Response toResponse(Exception exception) {
        
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        
        if (exception instanceof VESException) {
            status = ((VESException)exception).getStatus();
        }
        
        return Response.status(status).entity(exception.getMessage())
                .build();
    }
}
