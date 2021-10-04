package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getListUsers());
        return "users";
    }

    @GetMapping("/users/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "new";
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(required = false, name = "1") String ADMIN,
                             @RequestParam(required = false, name = "2") String USER) {
        Set<Role> roleSet = new HashSet<>();
        if (ADMIN != null) {
            roleSet.add(roleService.getRoleById(1));
        }
        if (USER != null) {
            roleSet.add(roleService.getRoleById(2));
        }
        if (USER == null & ADMIN == null) {
            roleSet.add(roleService.getRoleById(2));
        }
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.allRoles());
        return "edit";
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(required = false, name = "1") String ADMIN,
                             @RequestParam(required = false, name = "2") String USER) {
        Set<Role> roleSet = new HashSet<>();
        if (ADMIN != null) {
            roleSet.add(roleService.getRoleById(1));
        }
        if (USER != null) {
            roleSet.add(roleService.getRoleById(2));
        }
        if (USER == null & ADMIN == null) {
            roleSet.add(roleService.getRoleById(2));
        }
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("admin/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/user_{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String showUser(@PathVariable("name") String name, Model model) {
        model.addAttribute("user", userService.getUserByName(name));
        return "user";
    }

}
