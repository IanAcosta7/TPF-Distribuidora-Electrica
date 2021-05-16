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
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Field typename is required.")
    private String typename;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usertype", fetch = FetchType.LAZY)
    List<User> users;
}
