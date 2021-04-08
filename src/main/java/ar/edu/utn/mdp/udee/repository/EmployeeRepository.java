package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee getEmployeeByUsernameAndPassword(String username, String password);
}
