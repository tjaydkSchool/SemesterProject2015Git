/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.NotFoundException;
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
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    ServletContext context;

    @Override
    public Response toResponse(NotFoundException e) {
        boolean isDebug = context.getInitParameter("debug").equals("true");
        ErrorMessage em = new ErrorMessage(e, Response.Status.NOT_FOUND.getStatusCode(), isDebug);
        em.setMessage("The page you requested does not exist");
        return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(em)).type(MediaType.APPLICATION_JSON).build();
    }

}
