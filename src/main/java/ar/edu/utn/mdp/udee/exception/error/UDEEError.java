package ar.edu.utn.mdp.udee.exception.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UDEEError {
    private List<String> errors;
}
