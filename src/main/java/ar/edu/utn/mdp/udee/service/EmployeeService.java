package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Employee;
import ar.edu.utn.mdp.udee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Integer login(String username, String password) {
        Employee employee = employeeRepository.getEmployeeByUsernameAndPassword(username, password);
        Integer id = 0;

        if (employee != null) {
            id = employee.getId();
        }

        return id;
    }

    public Integer add(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public List<Employee> get() {
        return employeeRepository.findAll();
    }
}
