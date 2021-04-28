package ar.edu.utn.mdp.udee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "electricMeter_id", referencedColumnName = "id")
    private ElectricMeter electricMeter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", referencedColumnName = "id")
    private Bill bill;

    @NotNull(message = "Field measure is required.")
    @Min(value = 0, message = "Field measure must be a positive number.")
    private Float measure;

    @NotNull(message = "Field date is required.")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;
}
