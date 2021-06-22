package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.tariff.TariffTypeDTO;
import ar.edu.utn.mdp.udee.model.TariffType;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.TariffTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TariffTypeService {

    private final TariffTypeRepository tariffTypeRepository;
    private final ConversionService conversionService;

    @Autowired
    public TariffTypeService(TariffTypeRepository tariffTypeRepository, ConversionService conversionService) {
        this.tariffTypeRepository = tariffTypeRepository;
        this.conversionService = conversionService;
    }

    public PaginationResponse<TariffTypeDTO> getTariffTypes(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<TariffType> tariffTypePage = tariffTypeRepository.findAll(pageable);
        Page<TariffTypeDTO> tariffTypeDTOPage = tariffTypePage.map(tariffType -> conversionService.convert(tariffType, TariffTypeDTO.class));
        return new PaginationResponse<>(tariffTypeDTOPage.getContent(), tariffTypeDTOPage.getTotalPages(), tariffTypeDTOPage.getTotalElements());
    }

    public TariffTypeDTO getTariffTypeById(Integer id) {
        TariffType tariffType = tariffTypeRepository.findById(id).orElse(null);
        return conversionService.convert(tariffType, TariffTypeDTO.class);
    }

    public TariffTypeDTO addTariffType(TariffTypeDTO tariffTypeDTO) {
        TariffType tariffType = tariffTypeRepository.save(conversionService.convert(tariffTypeDTO, TariffType.class));
        return conversionService.convert(tariffType, TariffTypeDTO.class);
    }
}
