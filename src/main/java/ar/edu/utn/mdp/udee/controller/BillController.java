package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.bill.BillDTO;
import ar.edu.utn.mdp.udee.model.dto.range.DateRangeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping(BillController.PATH)
public class BillController {

    public static final String PATH = "/bills";

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<BillDTO>> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "50") Integer size,
            @RequestBody DateRangeDTO dateRangeDTO
    ) {
        return ResponseEntity.ok(
                billService.getAll(
                        PageRequest.of(page, size),
                        Date.valueOf(dateRangeDTO.getSince()),
                        Date.valueOf(dateRangeDTO.getUntil())
                )
        );
    }

}
