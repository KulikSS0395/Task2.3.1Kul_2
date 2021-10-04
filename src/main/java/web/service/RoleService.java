package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);
    List<Role> allRoles();
    Role getRoleById(long id);
}
