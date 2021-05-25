package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.TariffType;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.service.TariffService;
import ar.edu.utn.mdp.udee.service.TariffTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TariffController.PATH)
public class TariffController {

    final public static String PATH = "/Tariff";
    final public static String TYPE_PATH = "/Type";

    private final TariffService tariffService;
    private final TariffTypeService tariffTypeService;

    @Autowired
    public TariffController(TariffService tariffService, TariffTypeService tariffTypeService) {
        this.tariffService = tariffService;
        this.tariffTypeService = tariffTypeService;
    }

    @GetMapping
    public PaginationResponse<Tariff> getTariffs(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", defaultValue = "50") Integer size) {
        return tariffService.get(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tariff> getTariffById(@PathVariable Integer id) {
        return ResponseEntity.ok(tariffService.getTariffById(id));
    }

    @PostMapping
    public PostResponse addTariff(@RequestBody Tariff tariff) {
        return tariffService.addTariff(tariff);
    }

    @GetMapping(TYPE_PATH)
    public PaginationResponse<TariffType> getTariffTypes(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "50") Integer size) {
        return tariffTypeService.getTariffTypes(page, size);
    }

    @GetMapping(TYPE_PATH + "/{id}")
    public ResponseEntity<TariffType> getTariffTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(tariffTypeService.getTariffTypeById(id));
    }

    @PostMapping(TYPE_PATH)
    public PostResponse addTariffType(@RequestBody TariffType tariffType) {
        return tariffTypeService.addTariffType(tariffType);
    }
}
