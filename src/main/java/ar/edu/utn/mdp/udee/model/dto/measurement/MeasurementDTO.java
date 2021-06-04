package ar.edu.utn.mdp.udee.model.dto.measurement;

import ar.edu.utn.mdp.udee.model.Bill;
import ar.edu.utn.mdp.udee.model.dto.ElectricMeterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {
    private Integer id;
    private Bill bill;
    private ElectricMeterDTO electricMeter;
    private Float measure;
    private LocalDateTime measureDateTime;
}
