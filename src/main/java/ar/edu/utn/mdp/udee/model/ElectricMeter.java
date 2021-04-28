package ar.edu.utn.mdp.udee.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
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
