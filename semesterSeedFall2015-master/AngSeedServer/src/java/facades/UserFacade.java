package facades;

import entity.Reservation;
import deploy.DeploymentConfiguration;
import entity.User;
import exceptions.CustomNotFoundException;
import exceptions.UnprocessableEntityError;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import static org.eclipse.persistence.exceptions.DynamicException.entityNotFoundException;
import security.PasswordHash;

public class UserFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.puName);
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

    public void createUser(User user) throws UnprocessableEntityError {
        if (em.find(User.class, user.getUsername()) == null) {
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
        } else {
            throw new UnprocessableEntityError("Username already taken");
        }
    }

    public void updateUser(User user) throws CustomNotFoundException {
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
        } else {
            throw new CustomNotFoundException("Cannot update the user, as there is no user with the given username");
        }
    }

    public void deleteUser(String username) throws CustomNotFoundException {
        User u = em.find(User.class, username);
        if (u != null) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        } else {
            throw new CustomNotFoundException("Cannot delete the user, as there is no user with the given username");
        }
    }

    public List<Reservation> getReservations(String userName) throws CustomNotFoundException {
        if (em.find(User.class, userName) != null) {
            Query query = em.createNamedQuery("Reservation.findReservations");
            query.setParameter("userName", userName);
            if (!(query.getResultList().isEmpty())) {
                return query.getResultList();
            } else {
                throw new CustomNotFoundException("This user has no reservations");
            }
        } else {
            throw new CustomNotFoundException("There is no user with the given username");
        }
    }

    public void createUserAndAdmin() throws NoSuchAlgorithmException, InvalidKeySpecException {
        User admin = em.find(User.class, "Admin");
        if(admin == null) {
            User createAdmin = new User();
            User createUser = new User();
            
            createAdmin.setUsername("Admin");
            createAdmin.setPassword(PasswordHash.createHash("test"));
            createAdmin.AddRole("Admin");
            
            createUser.setUsername("User");
            createUser.setPassword(PasswordHash.createHash("test"));
            createUser.AddRole("User");
            
            em.getTransaction().begin();
            em.persist(createAdmin);
            em.persist(createUser);
            em.getTransaction().commit();
        }
    }
}
