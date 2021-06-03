package ar.edu.utn.mdp.udee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", referencedColumnName = "id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "electric_meter_id", referencedColumnName = "id")
    private ElectricMeter electricMeter;

    @NotNull(message = "Field measure is required.")
    @Min(value = 0, message = "Field measure must be a positive number.")
    private Float measure;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDateTime measureDateTime;
}
