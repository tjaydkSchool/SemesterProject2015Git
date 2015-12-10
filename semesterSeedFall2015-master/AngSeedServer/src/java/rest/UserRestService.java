package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.User;
import exceptions.UserNotFoundException;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user")
//@RolesAllowed("User")
public class UserRestService {
    
    Gson gson;
    UserFacade facade;
    
    public UserRestService() {
        gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        facade = new UserFacade();
    }
  
  
  @POST
  @Produces("application/json")
  @Consumes("application/json")
  public String createUser(String user) {
      facade.createUser(gson.fromJson(user, User.class));
      return user;
  }
  
  @GET
  @Path("{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getReservation(@PathParam("userName") String userName) {
    return gson.toJson(facade.getReservations(userName));
  }
  
  @PUT
  @Consumes("application/json")
  public String updateUser(String user) throws UserNotFoundException {
      facade.updateUser(gson.fromJson(user, User.class));
      return user;
  }
  
  @DELETE
  @Path("{username}")
  @Produces("application/json") 
  @Consumes("application/json")
  public String deleteUser(@PathParam("username") String username) throws UserNotFoundException {
      facade.deleteUser(username);
      return username;
  }
 
}