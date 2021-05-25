package ar.edu.utn.mdp.udee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressid", referencedColumnName = "id")
    private Address address;

    @NotNull(message = "Field date is required.")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date billDate;

    @NotNull(message = "Field payed is required.")
    @Min(value = 0, message = "Field payed must be a positive number.")
    private Float amountPayed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientid", referencedColumnName = "id")
    private User user;

}
