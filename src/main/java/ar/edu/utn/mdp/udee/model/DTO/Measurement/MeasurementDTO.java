package ar.edu.utn.mdp.udee.model.DTO.Measurement;

import ar.edu.utn.mdp.udee.model.Bill;
import ar.edu.utn.mdp.udee.model.DTO.ElectricMeterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {
    private Integer id;
    private Bill bill;
    private ElectricMeterDTO electricMeter;
    private Float measure;
    private Date measureDateTime;
}
