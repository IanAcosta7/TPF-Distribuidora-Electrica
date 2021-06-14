package ar.edu.utn.mdp.udee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Field street cannot be empty.")
    private String street;

    @NotBlank(message = "Field number cannot be empty.")
    private String addressNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_id", referencedColumnName = "id")
    private Tariff tariffs;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "electric_meter_id", referencedColumnName = "id")
    private ElectricMeter electricMeter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User client;
}
