/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static exceptions.CustomNotFoundExceptionMapper.gson;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Ebbe
 */
@Provider
public class NoAvailableTicketsExceptionMapper implements ExceptionMapper<NoAvailableTicketsException> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
       @Context
    ServletContext context;
       
    @Override
    public Response toResponse(NoAvailableTicketsException e) {
               boolean isDebug = context.getInitParameter("debug").toLowerCase().equals("true");
        ErrorMessage em = new ErrorMessage(e, 2, isDebug);
        return Response.status(2).entity(gson.toJson(em)).type(MediaType.APPLICATION_JSON).build();

    }
    
}
