/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author Dennis
 */
public class LoginFailedExceptionMapper implements ExceptionMapper<LoginFailedException>{

    @Context
    ServletContext context;
    
    @Override
    public Response toResponse(LoginFailedException e) {
        JsonObject jo = new JsonObject();
        if (Boolean.valueOf(context.getInitParameter("address"))) {
            jo.addProperty("Code:", 401);
        }
        jo.addProperty("Message", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(jo.toString()).build();
    }
    
}
