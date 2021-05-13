package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.TariffType;
import ar.edu.utn.mdp.udee.repository.TariffTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffTypeService {

    private TariffTypeRepository tariffTypeRepository;

    @Autowired
    public TariffTypeService(TariffTypeRepository tariffTypeRepository) {
        this.tariffTypeRepository = tariffTypeRepository;
    }

    public List<TariffType> getTariffTypes() {
        return tariffTypeRepository.findAll();
    }

    public TariffType getTariffTypeById(Integer id) {
        return tariffTypeRepository.findById(id).orElse(null);
    }

    public Integer addTariffType(TariffType tariffType) {
        return tariffTypeRepository.save(tariffType).getId();
    }
}
