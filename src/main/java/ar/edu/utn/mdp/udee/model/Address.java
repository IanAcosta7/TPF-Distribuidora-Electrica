package ar.edu.utn.mdp.udee.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Field street cannot be empty.")
    private String street;

    @NotBlank(message = "Field number cannot be empty.")
    private Integer number;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_id", referencedColumnName = "id")
    private List<Tariff> tariffs;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "electricMeter_id", referencedColumnName = "id")
    private ElectricMeter electricMeter;
}
