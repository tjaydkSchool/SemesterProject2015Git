/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Ebbe
 */
public class SearchFacade {

    private Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    private final static List<Future<JsonObject>> futures = new ArrayList();
    private final static ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SemesterProjectDbTestPU");
    private EntityManager em = emf.createEntityManager();
    
    
    public static void main(String[] args) throws IOException {
        SearchFacade f = new SearchFacade();
        System.out.println(f.getURLs("/api/flightinfo/CPH/2016-01-04T23:00:00.000Z/3").size());
    }

    public List<JsonObject> getURLs(String parameters) throws MalformedURLException, IOException {
        List<JsonObject> jsonObjectList = new ArrayList();
        Query query = em.createNamedQuery("URL.findAll");
        URL url;
        String jsonStr = "[";

        for (int i = 0; i < query.getResultList().size(); i++) {

            String urlString = query.getResultList().get(i).toString() + parameters;

            Future f = threadPool.submit(new getJsonFromUrlTask(urlString));
            futures.add(f);

//            url = new URL(query.getResultList().get(0).toString() + parameters);
//            
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
//            Scanner scan = new Scanner(con.getInputStream());
//
//            while (scan.hasNext()) {
//                jsonStr += scan.nextLine();
//            }
//            
//            scan.close();
//            if (i != query.getResultList().size() -1) {
//                jsonStr += ",";
//            }
        }

        System.out.println("Now all tasks are submittet");
        for (Future<JsonObject> future : futures) {
            try {
                jsonObjectList.add(future.get()); //get() metoden på en future er et blokkerende kald der afventer afslutningen af call metoden i den Callable der blev submittet til thread poolen.
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(SearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.printf("Now all tasks have completed. The size of the jsonObjectList is", jsonObjectList.size());
        threadPool.shutdown(); //Without this the jvm will continue to run.
//        jsonStr += "]";
        return jsonObjectList;
    }

    public class getJsonFromUrlTask implements Callable<JsonObject> {

        private String parameters;

        public getJsonFromUrlTask(String parameters) {
            this.parameters = parameters;
        }

        @Override
        public JsonObject call() throws Exception {
            JsonObject jo = null;
            String jsonStr = "";

            URL url = new URL(parameters);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            Scanner scan = new Scanner(con.getInputStream());

            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
            }
            scan.close();

            JsonElement je = new JsonParser().parse(jsonStr);
            jo = je.getAsJsonObject();
            
            return jo;
        }

    }
}
