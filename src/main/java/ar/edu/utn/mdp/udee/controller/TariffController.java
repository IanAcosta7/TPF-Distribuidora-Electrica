package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.TariffType;
import ar.edu.utn.mdp.udee.service.TariffService;
import ar.edu.utn.mdp.udee.service.TariffTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tariff")
public class TariffController {

    private TariffService tariffService;
    private TariffTypeService tariffTypeService;

    @Autowired
    public TariffController(TariffService tariffService, TariffTypeService tariffTypeService) {
        this.tariffService = tariffService;
        this.tariffTypeService = tariffTypeService;
    }

    @GetMapping
    public List<Tariff> getTariffs() {
        return tariffService.getTariffs();
    }

    @GetMapping("/{id}")
    public Tariff getTariffById(@PathVariable Integer id) {
        return tariffService.getTariffById(id);
    }

    @PostMapping
    public Integer addTariff(@RequestBody Tariff tariff) {
        return tariffService.addTariff(tariff);
    }

    @GetMapping("/type")
    public List<TariffType> getTariffTypes() {
        return tariffTypeService.getTariffTypes();
    }

    @GetMapping("/type/{id}")
    public TariffType getTariffTypeById(@PathVariable Integer id) {
        return tariffTypeService.getTariffTypeById(id);
    }

    @PostMapping("/type")
    public Integer addTariffType(@RequestBody TariffType tariffType) {
        return tariffTypeService.addTariffType(tariffType);
    }
}
