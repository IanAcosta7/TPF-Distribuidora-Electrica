package ar.edu.utn.mdp.udee.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTypeDTO {
    private Integer id;
    private String typeName;
}
