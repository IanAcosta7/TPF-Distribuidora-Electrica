package ar.edu.utn.mdp.udee.model.dto.meter;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricMeterDTO {
    private Integer id;
    private String serialNumber;
    private MeterModelDTO meterModel;
}
