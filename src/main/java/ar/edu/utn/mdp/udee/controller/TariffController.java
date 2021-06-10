package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.tariff.TariffDTO;
import ar.edu.utn.mdp.udee.model.dto.tariff.TariffTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.TariffService;
import ar.edu.utn.mdp.udee.service.TariffTypeService;
import ar.edu.utn.mdp.udee.util.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(TariffController.PATH)
public class TariffController {

    final public static String PATH = "/tariffs";
    final public static String TYPE_PATH = "/types";

    private final TariffService tariffService;
    private final TariffTypeService tariffTypeService;

    @Autowired
    public TariffController(TariffService tariffService, TariffTypeService tariffTypeService) {
        this.tariffService = tariffService;
        this.tariffTypeService = tariffTypeService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<TariffDTO>> getTariffs(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "50") Integer size) {
        return ResponseEntity.ok(tariffService.get(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffDTO> getTariffById(@PathVariable Integer id) {
        return ResponseEntity.ok(tariffService.getTariffById(id));
    }

    @PostMapping
    public ResponseEntity<TariffDTO> addTariff(@RequestBody TariffDTO tariffDTO) {
        TariffDTO tariffDTOAdded = tariffService.addTariff(tariffDTO);
        return ResponseEntity.created(
                URI.create(
                        EntityURLBuilder.buildURL(
                                TariffController.PATH,
                                tariffDTOAdded.getId()
                        )
                )
        ).body(tariffDTOAdded);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(tariffService.delete(id));
    }

    @GetMapping(TYPE_PATH)
    public ResponseEntity<PaginationResponse<TariffTypeDTO>> getTariffTypes(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                            @RequestParam(value = "size", defaultValue = "50") Integer size) {
        return ResponseEntity.ok(tariffTypeService.getTariffTypes(page, size));
    }

    @GetMapping(TYPE_PATH + "/{id}")
    public ResponseEntity<TariffTypeDTO> getTariffTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(tariffTypeService.getTariffTypeById(id));
    }

    @PostMapping(TYPE_PATH)
    public ResponseEntity<TariffTypeDTO> addTariffType(@RequestBody TariffTypeDTO tariffTypeDTO) {
        TariffTypeDTO tariffTypeDTOAdded = tariffTypeService.addTariffType(tariffTypeDTO);
        return ResponseEntity.created(
                URI.create(
                        EntityURLBuilder.buildURL(
                                TariffController.PATH + TariffController.TYPE_PATH,
                                tariffTypeDTOAdded.getId()
                        )
                )
        ).body(tariffTypeDTOAdded);
    }
}
