package ar.edu.utn.mdp.udee.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private UserTypeDTO usertype;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
