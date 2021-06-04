package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.TariffDTO;
import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TariffService {

    private final TariffRepository tariffRepository;
    private final ConversionService conversionService;

    @Autowired
    public TariffService(TariffRepository tariffRepository, ConversionService conversionService) {
        this.tariffRepository = tariffRepository;
        this.conversionService = conversionService;
    }

    public PaginationResponse<TariffDTO> get(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tariff> tariffPage = tariffRepository.findAll(pageable);
        Page<TariffDTO> tariffDTOPage = tariffPage.map(tariff -> conversionService.convert(tariff, TariffDTO.class));
        return new PaginationResponse<>(tariffDTOPage.getContent(), tariffDTOPage.getTotalPages(), tariffDTOPage.getTotalElements());
    }

    public TariffDTO addTariff(TariffDTO tariffDTO) {
        Tariff tariff = tariffRepository.save(conversionService.convert(tariffDTO, Tariff.class));
        return conversionService.convert(tariff, TariffDTO.class);
    }

    public TariffDTO getTariffById(Integer id) {
        Tariff tariff = tariffRepository.findById(id).orElse(null);
        return conversionService.convert(tariff, TariffDTO.class);
    }
}
