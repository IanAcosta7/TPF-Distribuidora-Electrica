package ar.edu.utn.mdp.udee.model.DTO.Measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMeasurementDTO {
    private Integer electricMeterId;
    private Date measureDateTime;
    private Float measure;
}
