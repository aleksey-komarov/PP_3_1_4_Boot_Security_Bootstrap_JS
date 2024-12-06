package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        long id = userService.findByUsername(principal.getName()).getId();
        User user = userService.getUser(id);
        if (user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }


    @GetMapping("/new")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/new")
    public String creatUser(@Valid @ModelAttribute("newUser") User user,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        if (!userService.uniqueUsername(user.getUsername())) {
            model.addAttribute("uniqueUsername", "Пользователь с таким логином существует");
            return "new";
        }
        userService.addUser(user);
        return "redirect:/admin";

    }

    @GetMapping("/edit")
    public String updateUser(@RequestParam("id") long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("editUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@Valid @ModelAttribute("editUser") User user, BindingResult bindingResult, @RequestParam("id") long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.updateUser(user, id);
        return "redirect:/admin";

    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
