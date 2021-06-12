package ar.edu.utn.mdp.udee.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConsumptionDTO {
    private Integer id;
    private UserTypeDTO userType;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Float consumed;
}
