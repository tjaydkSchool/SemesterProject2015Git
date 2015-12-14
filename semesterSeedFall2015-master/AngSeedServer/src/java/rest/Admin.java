package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.AdminFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("admin")
@RolesAllowed("Admin")
public class Admin {

    Gson gson;
    AdminFacade f;

    public Admin() {
        gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        f = new AdminFacade();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getReservations() {
        return gson.toJson(f.getReservations());
    }

}
