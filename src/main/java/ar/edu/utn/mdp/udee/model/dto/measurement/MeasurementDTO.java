package ar.edu.utn.mdp.udee.model.dto.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {
    private Integer id;
    private Integer billId;
    private Integer electricMeterId;
    private Float measure;
    private LocalDateTime measureDateTime;
    private Float price;
}
