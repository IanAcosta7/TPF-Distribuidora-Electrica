package ar.edu.utn.mdp.udee.model.dto.bill;

import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.MeasurementDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {

    private Integer id;
    private AddressDTO address;
    private MeasurementDTO initialMeasurement;
    private MeasurementDTO finalMeasurement;
    private Date billDate;
    private Float amountPayed;
    private Float consumption;
    private Float total;

}
