package ar.edu.utn.mdp.udee.model.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricMeterDTO {
    private Integer id;
    private String serialNumber;
    private MeterModelDTO meterModel;
}
