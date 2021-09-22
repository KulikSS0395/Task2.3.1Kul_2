package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User userUpdate) {
        User user = getUserById(id);
        user.setName(userUpdate.getName());
        user.setLastName(userUpdate.getLastName());
        user.setEmail(userUpdate.getEmail());
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> getListUsers() {
        return entityManager.createQuery("select u from User u").getResultList();
    }
}
