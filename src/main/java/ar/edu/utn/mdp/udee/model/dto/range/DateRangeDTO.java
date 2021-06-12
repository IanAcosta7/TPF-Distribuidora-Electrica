package ar.edu.utn.mdp.udee.model.dto.range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeDTO {
    private String since;
    private String until;
}
