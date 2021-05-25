package ar.edu.utn.mdp.udee.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PaginationResponse<T> implements Serializable {
    private List<T> content;
    private int totalPages;
    private long totalElements;
}
