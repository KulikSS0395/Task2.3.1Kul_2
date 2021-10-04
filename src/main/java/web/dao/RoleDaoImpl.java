package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Role> allRoles() {
        return entityManager.createQuery("select r from Role r").getResultList();
    }

    @Override
    public Role getRoleById(long id) {
        return entityManager.find(Role.class, id);
    }
}
