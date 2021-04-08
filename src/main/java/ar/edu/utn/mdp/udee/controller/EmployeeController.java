package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.Employee;
import ar.edu.utn.mdp.udee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public Integer login(@RequestHeader("authorization") String credentials) {
        String[] userData = credentials.split(" ")[1].split(":");
        String username = userData[0];
        String password = userData[1];
        return employeeService.login(username, password);
    }

    @PostMapping("/")
    public Integer add(@RequestBody Employee employee) {
        return employeeService.add(employee);
    }

    @GetMapping("/")
    public List<Employee> get() {
        return employeeService.get();
    }

}
