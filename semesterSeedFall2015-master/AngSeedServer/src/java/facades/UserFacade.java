package facades;

import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.PasswordHash;

public class UserFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SemesterProjectDbTestPU");
    private EntityManager em = emf.createEntityManager();

    public UserFacade() {

    }

    public void addUserTest() {
        User user = new User();
        user.setUsername("User");
        user.setPassword("Pass");
        user.AddRole("User");
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        User u = em.find(User.class, "User");
        System.out.println("Role: " + u.getRoles());
    }

    /**
     * Get a user from username
     *
     * @param id
     * @return
     */
    public User getUserByUserId(String username) {
        return em.find(User.class, username);
    }

    /**
     * Authenticate hashed password
     *
     * @param userName
     * @param password
     * @return
     */
    public List<String> authenticateUser(String username, String password) {
        User user = em.find(User.class, username);

        try {
            return user != null && (PasswordHash.validatePassword(password, user.getPassword())) ? user.getRoles() : null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void createUser(User user) {
        if (user != null) {
            try {
                user.AddRole("User");
                if (user.getPassword() != null) {
                    user.setPassword(PasswordHash.createHash(user.getPassword()));
                }
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateUser(User user) {
        if (em.find(User.class, user.getUsername()) != null) {
            try {
                if (user.getPassword() != null) {
                    user.setPassword(PasswordHash.createHash(user.getPassword()));
                }
                em.getTransaction().begin();
                em.merge(user);
                em.getTransaction().commit();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteUser(User user) {
        User u = em.find(User.class, user.getUsername());
        if (u != null) {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }
    }

}
