package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.ElectricMeterDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.ElectricMeterService;
import ar.edu.utn.mdp.udee.util.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ElectricMeterController.PATH)
public class ElectricMeterController {

    public final static String PATH = "/meters";

    private final ElectricMeterService electricMeterService;

    @Autowired
    public ElectricMeterController(ElectricMeterService electricMeterService) {
        this.electricMeterService = electricMeterService;
    }

    @PostMapping
    public ResponseEntity<ElectricMeterDTO> add(@RequestBody ElectricMeterDTO electricMeterDTO) {
        ElectricMeterDTO electricMeterDTOAdded = electricMeterService.addElectricMeter(electricMeterDTO);
        return ResponseEntity.created(
                URI.create(
                        EntityURLBuilder.buildURL(
                                ElectricMeterController.PATH,
                                electricMeterDTOAdded.getId()
                        )
                )
        ).body(electricMeterDTOAdded);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<ElectricMeterDTO>> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "50") Integer size
    ) {
        return ResponseEntity.ok(electricMeterService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectricMeterDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(electricMeterService.getById(id));
    }

}
