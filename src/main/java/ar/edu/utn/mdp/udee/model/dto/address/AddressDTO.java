package ar.edu.utn.mdp.udee.model.dto.address;

import ar.edu.utn.mdp.udee.model.ElectricMeter;
import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Integer id;
    private String street;
    private String addressNumber;
    private List<Tariff> tariffs;
    private ElectricMeter electricMeter;
    private User client;

}
