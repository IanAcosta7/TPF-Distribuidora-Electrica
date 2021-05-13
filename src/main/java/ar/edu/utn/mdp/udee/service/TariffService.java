package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {

    private TariffRepository tariffRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public List<Tariff> getTariffs() {
        return tariffRepository.findAll();
    }

    public Integer addTariff(Tariff tariff) {
        return tariffRepository.save(tariff).getId();
    }

    public Tariff getTariffById(Integer id) {
        return tariffRepository.findById(id).orElse(null);
    }
}
