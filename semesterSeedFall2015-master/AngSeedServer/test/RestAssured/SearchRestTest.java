/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestAssured;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;

/**
 *
 * @author Ebbe
 */
public class SearchRestTest {

    static Server server;

    public SearchRestTest() {
        baseURI = "http://localhost:8082";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8082);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }

    @Test
    public void testSearchOriginDateTickets() {
        given().
                contentType("application/json").
                when().
                get("/flightinfo/CPH/2016-01-04T23:00:00.000Z/3").
                then().
                statusCode(200);
    }

    @Test
    public void testSearchOriginDateTicketsParameterMissing() {
        given().
                contentType("application/json").
                when().
                get("/flightinfo/CPH/3").
                then().
                statusCode(404);
    }

    @Test
    public void testSearchOriginDateTicketsWrongParameter() {
        given().
                contentType("application/json").
                when().
                get("/flightinfo/NOTANAIRPORT/2016-01-04T23:00:00.000Z/3").
                then().
                statusCode(1);
    }

    @Test
    public void testSearchOriginDestinationDateTickets() {
        given().
                contentType("application/json").
                when().
                get("/flightinfo/CPH/STN/2016-01-04T23:00:00.000Z/3").
                then().
                statusCode(200);
    }
}
