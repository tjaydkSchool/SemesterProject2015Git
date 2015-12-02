package JUnit;

import entity.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import security.PasswordHash;

/**
 *
 * @author Dennis
 */
public class UserJUnitTest {
    
    UserFacade uf = new UserFacade();
    
    public UserJUnitTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    
//    TESTING ALL THE CRUD METHODS FOR THE USER
    @Test
    public void testCRUDUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");
        
//        PERSIST USER IN DB
        uf.createUser(user);
        
        
//        GET USER FROM DB
        assertNotNull(uf.getUserByUserId("testuser"));
        
//        UPDATE USER AND MERGE
        user.AddRole("Test");
        uf.updateUser(user);
        assertTrue(uf.getUserByUserId("testuser").getRoles().contains("Test"));
        
//        DELETE USER
        uf.deleteUser(user);
        assertNull(uf.getUserByUserId("testuser"));
    }
    
//    TESTING THAT ROLE AS USER IS AUTOMATICALLY ADDED WHEN CREATING USER
    @Test
    public void testUserRoleAdded() {
        User user = new User();
        user.setUsername("test");
        
        uf.createUser(user);
        
        assertTrue(uf.getUserByUserId("test").getRoles().contains("User"));
        
        uf.deleteUser(user);
    }
    
//    AUTHENTICATE PASSWORD
    @Test
    public void testAuthenticatedPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User();
        user.setUsername("test");
        user.setPassword("pass");
        uf.createUser(user);
        
        assertTrue(PasswordHash.validatePassword("pass", uf.getUserByUserId("test").getPassword()));
        
        uf.deleteUser(user);
    }
    
}
