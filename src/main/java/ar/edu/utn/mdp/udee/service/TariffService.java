package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.controller.TariffController;
import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.repository.TariffRepository;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {

    private TariffRepository tariffRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public PaginationResponse<Tariff> get(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tariff> tariffPage = tariffRepository.findAll(pageable);
        return new PaginationResponse<>(tariffPage.getContent(), tariffPage.getTotalPages(), tariffPage.getTotalElements());
    }

    public PostResponse addTariff(Tariff tariffToAdd) {
        Tariff tariff = tariffRepository.save(tariffToAdd);
        return new PostResponse(
                EntityURLBuilder.buildURL(
                        TariffController.PATH,
                        tariff.getId()
                ),
                HttpStatus.CREATED
        );
    }

    public Tariff getTariffById(Integer id) {
        return tariffRepository.findById(id).orElse(null);
    }
}
