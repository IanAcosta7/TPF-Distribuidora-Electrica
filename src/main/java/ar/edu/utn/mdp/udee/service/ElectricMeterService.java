package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.ElectricMeterDTO;
import ar.edu.utn.mdp.udee.model.ElectricMeter;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.ElectricMeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ElectricMeterService {

    private final ElectricMeterRepository electricMeterRepository;
    private final ConversionService conversionService;

    @Autowired

    public ElectricMeterService(ElectricMeterRepository electricMeterRepository, ConversionService conversionService) {
        this.electricMeterRepository = electricMeterRepository;
        this.conversionService = conversionService;
    }

    public ElectricMeterDTO addElectricMeter(ElectricMeterDTO electricMeterDTO) {
        ElectricMeter electricMeter = conversionService.convert(electricMeterDTO, ElectricMeter.class);

        if (electricMeter == null)
            throw new NullPointerException();

        electricMeter.setSerialNumber(UUID.randomUUID().toString());

        return conversionService.convert(electricMeterRepository.save(electricMeter), ElectricMeterDTO.class);
    }

    public PaginationResponse<ElectricMeterDTO> getAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ElectricMeter> electricMeterPage = electricMeterRepository.findAll(pageable);
        Page<ElectricMeterDTO> electricMeterDTOPage = electricMeterPage.map(electricMeter -> conversionService.convert(electricMeter, ElectricMeterDTO.class));
        return new PaginationResponse<>(electricMeterDTOPage.getContent(), electricMeterDTOPage.getTotalPages(), electricMeterDTOPage.getTotalElements());
    }

    public ElectricMeterDTO getById(Integer id) {
        return conversionService.convert(electricMeterRepository.findById(id).orElse(null), ElectricMeterDTO.class);
    }

}