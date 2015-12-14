/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import deploy.DeploymentConfiguration;
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
import static utils.LoggerHandler.log;

/**
 *
 * @author Ebbe
 */
public class SearchFacade {

    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.puName);
    private EntityManager em = emf.createEntityManager();

    public static void main(String[] args) throws IOException {
        SearchFacade f = new SearchFacade();
        System.out.println(f.getURLs("/api/flightinfo/CPH/SXF/2016-01-04T23:00:00.000Z/3").size());
    }

    public List<JsonObject> getURLs(String parameters) throws MalformedURLException, IOException {
        try {
            List<Future<JsonObject>> futures = new ArrayList();
            List<JsonObject> jsonObjectList = new ArrayList();
            Query query = em.createNamedQuery("URL.findAll");

            ExecutorService threadPool = Executors.newFixedThreadPool(4);

            for (int i = 0; i < query.getResultList().size(); i++) {
                Future f = threadPool.submit(new GetJsonFromUrlTask(query.getResultList().get(i).toString() + parameters));
                futures.add(f);
            }

            for (Future<JsonObject> future : futures) {
                try {
                    jsonObjectList.add(future.get()); //get() metoden på en future er et blokkerende kald der afventer afslutningen af call metoden i den Callable der blev submittet til thread poolen.
                } catch (InterruptedException ex) {
                    Logger.getLogger(SearchFacade.class.getName()).log(Level.SEVERE, null, ex); // SHOULD BE REPLACED WITH TIMESTAMP ERROR MESSAGE IN LOGFILE
                } catch (ExecutionException ex) {
                    Logger.getLogger(SearchFacade.class.getName()).log(Level.SEVERE, null, ex); // SHOULD BE REPLACED WITH TIMESTAMP ERROR MESSAGE IN LOGFILE
                }
            }
            threadPool.shutdown(); //Without this the jvm will continue to run.
            return jsonObjectList;
        } catch (Exception ex) {
            Logger.getLogger(SearchFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }

    
    public class GetJsonFromUrlTask implements Callable<JsonObject> {

        private String parameters;

        public GetJsonFromUrlTask(String parameters) {
            this.parameters = parameters;
        }

        @Override
        public JsonObject call() throws Exception {
            JsonObject jo = null;
            String jsonStr = "";

            try {
                URL url = new URL(parameters);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Accept", "application/json;charset=UTF-8");
                con.setConnectTimeout(5000); // TIMEOUT AFTER 5 SECONDS

                Scanner scan = new Scanner(con.getInputStream());

                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
                scan.close();

                JsonElement je = new JsonParser().parse(jsonStr);
                jo = je.getAsJsonObject();
                con.disconnect();

            } catch (java.net.SocketTimeoutException e) {
                System.out.println("Connection timed out"); // SHOULD BE REPLACED WITH TIMESTAMP ERROR MESSAGE IN LOGFILE
                log.info("Connection timed out on url: " + parameters);
            } catch (java.io.IOException e) {
                System.out.println("Input/Output error"); // SHOULD BE REPLACED WITH TIMESTAMP ERROR MESSAGE IN LOGFILE
            }

            return jo;
        }

    }
}
