package ar.edu.utn.mdp.udee.model.dto.consumption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionDTO {
    Float totalConsumption;
    Float totalPrice;
}
