package ar.edu.utn.mdp.udee.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class PostResponse {
    private String url;
    private HttpStatus status;
}
