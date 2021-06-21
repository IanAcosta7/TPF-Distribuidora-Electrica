package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.ElectricMeter;
import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
import ar.edu.utn.mdp.udee.model.dto.consumption.ConsumptionDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ConversionService conversionService;
    private final ElectricMeterService electricMeterService;
    private final TariffService tariffService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, ConversionService conversionService, ElectricMeterService electricMeterService, TariffService tariffService) {
        this.measurementRepository = measurementRepository;
        this.conversionService = conversionService;
        this.electricMeterService = electricMeterService;
        this.tariffService = tariffService;
    }

    public MeasurementDTO addMeasurement(NewMeasurementDTO newMeasurementDTO) throws LoginException {
        ElectricMeter electricMeter = electricMeterService.loginMeter(
                newMeasurementDTO.getSerialNumber(),
                newMeasurementDTO.getPassword())
                .orElseThrow(() -> new LoginException("Wrong electric meter credentials."));

        Tariff tariff = tariffService.getTariffFromMeter(electricMeter.getId());

        Measurement lastMeasure = measurementRepository.getTopByElectricMeter(electricMeter.getId());

        float actualMeasure;

        if (lastMeasure != null)
            actualMeasure = newMeasurementDTO.getValue() - lastMeasure.getMeasure();
        else
            actualMeasure = newMeasurementDTO.getValue();

        Measurement measurement = new Measurement(
                null,
                null,
                electricMeter,
                newMeasurementDTO.getValue(),
                LocalDateTime.parse(newMeasurementDTO.getDate()),
                tariff.getTariffValue() * actualMeasure
        );
        return conversionService.convert(measurementRepository.save(measurement), MeasurementDTO.class);
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

    public PaginationResponse<MeasurementDTO> getAllByAddressId(Integer addressId, Pageable pageable, LocalDateTime sinceMeasureDateTime, LocalDateTime untilMeasureDateTime) {
        Page<Measurement> measurementPage = measurementRepository.findRangeFromAddress(sinceMeasureDateTime, untilMeasureDateTime, addressId, pageable);
        Page<MeasurementDTO> measurementDTOPage = measurementPage.map(measurement -> conversionService.convert(measurement, MeasurementDTO.class));
        return new PaginationResponse<>(measurementDTOPage.getContent(), measurementDTOPage.getTotalPages(), measurementDTOPage.getTotalElements());
    }

    public PaginationResponse<MeasurementDTO> getAllByUserId(Integer id, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Measurement> measurementPage = measurementRepository.findByUserId(id, pageable);
        Page<MeasurementDTO> measurementDTOPage = measurementPage.map(measurement -> conversionService.convert(measurement, MeasurementDTO.class));
        return new PaginationResponse<>(measurementDTOPage.getContent(), measurementDTOPage.getTotalPages(), measurementDTOPage.getTotalElements());
    }

    public ConsumptionDTO getConsumption(int clientId, LocalDateTime sinceMeasureDateTime, LocalDateTime untilMeasureDateTime) {
        List<Measurement> measurementList = measurementRepository.getRangeOfInvoicedMeasurementsByUser(clientId, sinceMeasureDateTime, untilMeasureDateTime);

        if (measurementList.size() < 1)
            return null;

        float totalPrice = 0;

        for (int i = 0; i < measurementList.size(); i++) {
            totalPrice += measurementList.get(i).getPrice();
        }

        // This is not ok, partial consumption should be saved for each measurement...
        Measurement previousMeasure = measurementRepository.findById(measurementList.get(0).getId() - 1).orElse(null);
        Float totalConsumption = measurementList.get(measurementList.size() - 1).getMeasure() - (previousMeasure == null ? 0 : previousMeasure.getMeasure());

        return new ConsumptionDTO(totalConsumption, totalPrice);
    }
}
