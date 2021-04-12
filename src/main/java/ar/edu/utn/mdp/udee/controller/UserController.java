package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Integer login(@RequestHeader("authorization") String credentials) {
        String[] userData = credentials.split(" ")[1].split(":");
        String username = userData[0];
        String password = userData[1];
        return userService.login(username, password);
    }

    @PostMapping("/")
    public Integer add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/")
    public List<User> get() {
        return userService.get();
    }

}
