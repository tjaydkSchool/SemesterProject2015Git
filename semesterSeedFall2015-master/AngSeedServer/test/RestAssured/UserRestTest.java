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
import entity.User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;

/**
 *
 * @author Ebbe
 */
public class UserRestTest {

    static Server server;

    public UserRestTest() {
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
    public void Login() {
        //Successful login
        given().
                contentType("application/json").
                body(new User("newUser", "test")).
                when().
                post("/user").
                then().
                statusCode(200);
        given().
                contentType("application/json").
                body("{'username':'newUser','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200);
        given().
                contentType("application/json").
                when().
                delete("/user/newUser").
                then().
                statusCode(200);

    }

    @Test
    public void LoginWrongUsername() {
        //wrong username
        given().
                contentType("application/json").
                body("{'username':'notUsername','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Ilegal username or password"));
    }

    @Test
    public void LoginWrongPassword() {
        //wrong password
        given().
                contentType("application/json").
                body("{'username':'user','password':'notPassword'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Ilegal username or password"));
    }

    @Test
    public void LoginWrongUsernameAndPassword() {
        //wrong username and password
        given().
                contentType("application/json").
                body("{'username':'notUsername','password':'notPassword'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Ilegal username or password"));

    }

    @Test
    public void PostPutDeleteUserTest() {
//      updage user
//      delete user
        given().
                contentType("application/json").
                body(new User("newUser", "test")).
                when().
                post("/user").
                then().
                statusCode(200);
        given().
                contentType("application/json").
                body(new User("newUser", "newPassword")).
                when().
                put("/user").
                then().
                statusCode(200);
        given().
                contentType("application/json").
                when().
                delete("/user/newUser").
                then().
                statusCode(200);
    }

    @Test
    //get reservations
    public void GetUserReservations() {
        given().
                contentType("application/json").
                when().
                get("/user/test@test.dk").
                then().
                statusCode(200);
    }

    @Test
    //get reservations when there are none
    public void GetUserReservationsNoReservations() {
        given().
                contentType("application/json").
                when().
                get("/user/thisIsNotAUser").
                then().
                statusCode(404).
                body("error.message", equalTo("There is no user with the given username"));
    }

}
