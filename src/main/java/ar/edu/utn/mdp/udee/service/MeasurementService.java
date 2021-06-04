package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.NewMeasurementDTO;
import ar.edu.utn.mdp.udee.model.Measurement;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ConversionService conversionService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, ConversionService conversionService) {
        this.measurementRepository = measurementRepository;
        this.conversionService = conversionService;
    }

    public MeasurementDTO addMeasurement(NewMeasurementDTO newMeasurementDTO) {
        Measurement measurement = measurementRepository.save(conversionService.convert(newMeasurementDTO, Measurement.class));
        return conversionService.convert(measurement, MeasurementDTO.class);
    }

    public PaginationResponse<MeasurementDTO> getAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Measurement> measurementPage = measurementRepository.findAll(pageable);
        Page<MeasurementDTO> measurementDTOPage = measurementPage.map(measurement -> conversionService.convert(measurement, MeasurementDTO.class));
        return new PaginationResponse<>(measurementDTOPage.getContent(), measurementDTOPage.getTotalPages(), measurementDTOPage.getTotalElements());
    }

    public MeasurementDTO getById(Integer id) {
        return conversionService.convert(measurementRepository.findById(id).orElse(null), MeasurementDTO.class);
    }
}
