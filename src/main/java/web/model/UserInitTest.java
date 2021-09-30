package web.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.service.RoleService;
import web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserInitTest {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserInitTest(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostConstruct
    public void generateUsersAndRoleTest() {
        Role role1 = new Role(1L,"ROLE_ADMIN");
        Role role2 = new Role(2L, "ROLE_USER");
        Set<Role> allRole = new HashSet<>();
        allRole.add(role1);
        allRole.add(role2);

        User simpleUser = new User("Ivan", "Vanko",
                                "vanko@mail.ru", "0000", Collections.singleton(role2));
        User adminUser = new User("admin", "admin",
                                "kirk@bk.ru", "admin", allRole);


        roleService.addRole(role1);
        roleService.addRole(role2);

        userService.addUser(simpleUser);
        userService.addUser(adminUser);
    }

}
