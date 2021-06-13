package ar.edu.utn.mdp.udee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_types")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Field typename is required.")
    private String typeName;
}
