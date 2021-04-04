package ar.edu.utn.mdp.udee.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Employee {
    @Setter(AccessLevel.PRIVATE) private String username;
    private String name;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(username, employee.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
