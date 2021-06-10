package ar.edu.utn.mdp.udee.model.dto.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMeasurementDTO {
    private String serialNumber;
    private float value;
    private String date;
    private String password;
}
