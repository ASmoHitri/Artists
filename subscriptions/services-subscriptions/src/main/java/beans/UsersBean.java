package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.User;
import response_entities.ResponseUser;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsersBean {

    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "subscriptions-jpa")
    private EntityManager entityManager;

    public List<ResponseUser> getUsers() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<User> users = JPAUtils.queryEntities(entityManager, User.class, queryParameters);
        List<ResponseUser> responseUsers = new ArrayList<>();
        for(User user: users) {
            responseUsers.add(new ResponseUser(user));
        }
        return responseUsers;
    }

    public ResponseUser getUser(int userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null){
            return new ResponseUser(user);
        }
        return null;
    }

    public boolean addUser(User user) {
        if (user != null) {
            // generate password salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // hash password
            try {
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec spec = new PBEKeySpec(user.getPassword().toCharArray(), salt, 10000, 256);
                String hashedPassword = new String(secretKeyFactory.generateSecret(spec).getEncoded());
                user.setPassword(hashedPassword);
                user.setPasswordSalt(new String(salt));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                return false;
            }

            try{
                beginTx();
                entityManager.persist(user);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
                return false;
            }
            return true;
        }
        return false;
    }

    public User updateUser(int userId, User user) {
        if (getUser(userId) == null || user == null) {
            return null;
        }
        try{
            // TODO - svoj query za update - password se ne updata s klicem te metode
            beginTx();
            user.setId(userId);
            //entityManager.merge(user);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
        return user;
    }

    public boolean removeUser(int userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            try{
                beginTx();
                entityManager.remove(user);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
                return false;
            }
            return true;
        }
        return false;
    }

    private void beginTx() {
        if (!entityManager.getTransaction().isActive())
            entityManager.getTransaction().begin();
    }

    private void commitTx() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }

    private void rollbackTx() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().rollback();
    }
}