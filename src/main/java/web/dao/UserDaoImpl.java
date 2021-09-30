package web.dao;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        Query query = entityManager.createQuery("delete from User where id = :id ");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> getListUsers() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User getUserByName(String name) {
        return entityManager.createQuery("select u FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return entityManager.createQuery("select u FROM User u WHERE u.name = :name", User.class)
                                    .setParameter("name", username).getSingleResult();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
