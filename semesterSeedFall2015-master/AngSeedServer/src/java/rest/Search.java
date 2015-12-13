package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import facades.LogFacade;
import facades.SearchFacade;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Ebbe
 */
@Path("flightinfo")
public class Search {

    SearchFacade sf = new SearchFacade();
    LogFacade lf = new LogFacade();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    JsonParser jp = new JsonParser();

    @GET
    @Produces("application/json")
    @Path("{from}/{date}/{persons}")
    public String searchOriginDateTickets(@PathParam("from") String from, @PathParam("date") String date, @PathParam("persons") String persons) throws IOException {
        String params = "/api/flightinfo/" + from + "/" + date + "/" + persons;
//        JsonElement je = jp.parse(sf.getURLs(params));
        return gson.toJson(sf.getURLs(params));
    }

    @GET
    @Produces("application/json")
    @Path("{from}/{to}/{date}/{persons}")
    public String searchOriginDestinationDateTickets(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("persons") String persons) throws IOException {
        String params = "/api/flightinfo/" + from + "/" + to + "/" + date + "/" + persons;
//        JsonElement je = jp.parse(sf.getURLs(params));
        lf.LogDestination(to);
        return gson.toJson(sf.getURLs(params));
    }
    
    @GET
    @Produces("application/json")
    @Path("hotdestinations")
    public String getHotDestinations() {
        return gson.toJson(lf.getHotDestinations());
    }

}
