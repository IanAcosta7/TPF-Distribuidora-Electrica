package ar.edu.utn.mdp.udee.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @PostMapping("/employee/login")
    public Integer employee(String username, String password) {
        return 1;
    }

}
