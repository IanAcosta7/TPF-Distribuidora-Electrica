package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.DTO.Measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.DTO.Measurement.NewMeasurementDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.MeasurementService;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(MeasurementController.PATH)
public class MeasurementController {

    public final static String PATH = "/Measurement";

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping
    private ResponseEntity<MeasurementDTO> add(@RequestBody NewMeasurementDTO newMeasurementDTO) {
        MeasurementDTO measurementDTO = measurementService.addMeasurement(newMeasurementDTO);
        return ResponseEntity.created(
                URI.create(
                        EntityURLBuilder.buildURL(
                                UserController.PATH,
                                measurementDTO.getId()
                        )
                )
        ).body(measurementDTO);
    }

    @GetMapping
    private ResponseEntity<PaginationResponse<MeasurementDTO>> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "50") Integer size
    ) {
        return ResponseEntity.ok(measurementService.getAll(page, size));
    }

    @GetMapping("/{id}")
    private ResponseEntity<MeasurementDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(measurementService.getById(id));
    }

}
