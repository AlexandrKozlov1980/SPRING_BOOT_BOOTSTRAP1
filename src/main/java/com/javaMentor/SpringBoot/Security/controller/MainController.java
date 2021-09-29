package com.javaMentor.SpringBoot.Security.controller;

import com.javaMentor.SpringBoot.Security.model.User;
import com.javaMentor.SpringBoot.Security.service.RoleService;
import com.javaMentor.SpringBoot.Security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    private final RoleService roleService;

    private final UserService userService;

    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String getMain(Model model){
        model.addAttribute("currentUser",  userService.findUserByName(userService.getCurrentUsername()));
        model.addAttribute("users", userService.showAllUsers());
        model.addAttribute("allRoles", roleService.allRoles());
        model.addAttribute("newUser", new User());
        return "adminBT";
    }

    @GetMapping("/user")
    public String getUserPage(Model model){
        model.addAttribute("user",  userService.findUserByName(userService.getCurrentUsername()));
        return "userBT";
    }

    @PostMapping()
    public String create(@ModelAttribute("newUser") User user, @RequestParam(value = "roles", required = false) String[] roles){
        userService.createUser(user, roles);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "roles", required = false) String[] roles){
        userService.updateUser(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Integer id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
