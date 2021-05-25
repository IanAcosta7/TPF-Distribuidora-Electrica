package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.controller.TariffController;
import ar.edu.utn.mdp.udee.model.TariffType;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.repository.TariffTypeRepository;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffTypeService {

    private TariffTypeRepository tariffTypeRepository;

    @Autowired
    public TariffTypeService(TariffTypeRepository tariffTypeRepository) {
        this.tariffTypeRepository = tariffTypeRepository;
    }

    public PaginationResponse<TariffType> getTariffTypes(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<TariffType> tariffTypePage = tariffTypeRepository.findAll(pageable);
        return new PaginationResponse<>(tariffTypePage.getContent(), tariffTypePage.getTotalPages(), tariffTypePage.getTotalElements());
    }

    public TariffType getTariffTypeById(Integer id) {
        return tariffTypeRepository.findById(id).orElse(null);
    }

    public PostResponse addTariffType(TariffType tariffTypeToAdd) {
        TariffType tariffType = tariffTypeRepository.save(tariffTypeToAdd);
        return new PostResponse(
                EntityURLBuilder.buildURL(
                        TariffController.PATH + TariffController.TYPE_PATH,
                        tariffType.getId()
                ),
                HttpStatus.CREATED
        );
    }
}
