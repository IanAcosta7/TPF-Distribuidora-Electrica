package ar.edu.utn.mdp.udee.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Field street cannot be empty.")
    private String street;

    @NotBlank(message = "Field number cannot be empty.")
    private Integer number;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariffid", referencedColumnName = "id")
    private List<Tariff> tariffs;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "electricmeterid", referencedColumnName = "id")
    private ElectricMeter electricMeter;
}
