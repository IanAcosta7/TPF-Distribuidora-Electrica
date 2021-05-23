package ar.edu.utn.mdp.udee.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "electric_meters")
public class ElectricMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter(AccessLevel.NONE)
    private UUID serialNumber = UUID.randomUUID();

    @NotNull(message = "Field brand is required.")
    private String brand;

    @NotNull(message = "Field model is required.")
    private String model;

}
