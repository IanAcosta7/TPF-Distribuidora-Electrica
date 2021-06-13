package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
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

import java.time.LocalDateTime;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ConversionService conversionService;
    private final AddressService addressService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, ConversionService conversionService, AddressService addressService) {
        this.measurementRepository = measurementRepository;
        this.conversionService = conversionService;
        this.addressService = addressService;
    }

    public MeasurementDTO addMeasurement(NewMeasurementDTO newMeasurementDTO) {
        Measurement measurement = measurementRepository.save(conversionService.convert(newMeasurementDTO, Measurement.class));
        return conversionService.convert(measurement, MeasurementDTO.class);
    }

    /**
     * Retrieves all measurements in a date range.
     * @param pageable Pageable of the request.
     * @param sinceMeasureDateTime Min date.
     * @param untilMeasureDateTime Max date.
     * @return All measurements in a specified date range.
     */
    public PaginationResponse<MeasurementDTO> getAll(Pageable pageable, LocalDateTime sinceMeasureDateTime, LocalDateTime untilMeasureDateTime) {
        Page<Measurement> measurementPage = measurementRepository.findRange(sinceMeasureDateTime, untilMeasureDateTime, pageable);
        Page<MeasurementDTO> measurementDTOPage = measurementPage.map(measurement -> conversionService.convert(measurement, MeasurementDTO.class));
        return new PaginationResponse<>(measurementDTOPage.getContent(), measurementDTOPage.getTotalPages(), measurementDTOPage.getTotalElements());
    }

    /**
     * Retrieves all measurements in a date range for a specific user.
     * @param pageable Pageable of the request.
     * @param sinceMeasureDateTime Min date.
     * @param untilMeasureDateTime Max date.
     * @param userId ID of the measurements owner.
     * @return All measurements in a specified date range for a specific user.
     */
    public PaginationResponse<MeasurementDTO> getAll(Pageable pageable, LocalDateTime sinceMeasureDateTime, LocalDateTime untilMeasureDateTime, Integer userId) {
        Page<Measurement> measurementPage = measurementRepository.findRangeFromUser(sinceMeasureDateTime, untilMeasureDateTime, userId, pageable);
        Page<MeasurementDTO> measurementDTOPage = measurementPage.map(measurement -> conversionService.convert(measurement, MeasurementDTO.class));
        return new PaginationResponse<>(measurementDTOPage.getContent(), measurementDTOPage.getTotalPages(), measurementDTOPage.getTotalElements());
    }

    public MeasurementDTO getById(Integer id) {
        return conversionService.convert(measurementRepository.findById(id).orElse(null), MeasurementDTO.class);
    }

    public PaginationResponse<MeasurementDTO> getAllByUserId(Integer id, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Measurement> measurementPage = measurementRepository.findByUserId(id, pageable);
        Page<MeasurementDTO> measurementDTOPage = measurementPage.map(measurement -> conversionService.convert(measurement, MeasurementDTO.class));
        return new PaginationResponse<>(measurementDTOPage.getContent(), measurementDTOPage.getTotalPages(), measurementDTOPage.getTotalElements());
    }
}
