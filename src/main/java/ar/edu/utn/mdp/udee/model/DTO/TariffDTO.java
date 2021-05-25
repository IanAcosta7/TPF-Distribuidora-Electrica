package ar.edu.utn.mdp.udee.model.DTO;

import ar.edu.utn.mdp.udee.model.TariffType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffDTO {
    private Integer id;
    private TariffType tariffType;
    private float tariffValue;
}
