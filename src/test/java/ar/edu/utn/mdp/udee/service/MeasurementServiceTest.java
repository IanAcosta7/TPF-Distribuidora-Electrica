
package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.ElectricMeter;
import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.dto.measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.NewMeasurementDTO;
import ar.edu.utn.mdp.udee.model.Measurement;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.MeasurementRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class MeasurementServiceTest {

    private MeasurementService measurementService;
    private MeasurementRepository measurementRepositoryMock;
    private ConversionService conversionServiceMock;
    private ElectricMeterService electricMeterServiceMock;
    private TariffService tariffServiceMock;

    @BeforeAll
    public void setUp() {
        measurementRepositoryMock = mock(MeasurementRepository.class);
        conversionServiceMock = mock(ConversionService.class);
        electricMeterServiceMock = mock(ElectricMeterService.class);
        tariffServiceMock = mock(TariffService.class);

        measurementService = new MeasurementService(measurementRepositoryMock, conversionServiceMock, electricMeterServiceMock, tariffServiceMock);
    }

    @Test
    public void addMeasurementTest() {
        // Arrange
        NewMeasurementDTO newMeasurementDTO = getNewMeasurementDTO();
        ElectricMeter electricMeter = getElectricMeter();
        Tariff tariff = getTariff();
        Measurement measurement = getMeasurement();
        MeasurementDTO measurementDTO = getMeasurementDTO();
        Mockito.when(electricMeterServiceMock.loginMeter(newMeasurementDTO.getSerialNumber(), newMeasurementDTO.getPassword())).thenReturn(Optional.of(electricMeter));
        Mockito.when(tariffServiceMock.getTariffFromMeter(electricMeter.getId())).thenReturn(tariff);
        Mockito.when(measurementRepositoryMock.getTopByElectricMeter(electricMeter.getId())).thenReturn(measurement);
        Mockito.when(measurementRepositoryMock.save(Mockito.any(Measurement.class))).thenReturn(getMeasurement());
        Mockito.when(conversionServiceMock.convert(Mockito.any(Measurement.class), eq(MeasurementDTO.class))).thenReturn(measurementDTO);

        // Act
        MeasurementDTO result = null;
        try {
            result = measurementService.addMeasurement(newMeasurementDTO);
        } catch (Exception e) {
            Assertions.fail();
        }

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals((newMeasurementDTO.getValue() - measurement.getMeasure()) * tariff.getTariffValue(), result.getPrice());
    }

    @Test
    public void getAllTest() {
        // Arrange
        Integer pageNumber = 0;
        Integer pageSize = 50;
        Pageable pageable = getPageable(pageNumber, pageSize);
        LocalDateTime time = LocalDateTime.now();
        MeasurementDTO measurementDTO = getMeasurementDTO();
        Mockito.when(measurementRepositoryMock.findRange(time, time, pageable)).thenReturn(getMeasurementPage(pageable));
        Mockito.when(conversionServiceMock.convert(Mockito.any(Measurement.class), eq(MeasurementDTO.class))).thenReturn(measurementDTO);

        // Act
        PaginationResponse<MeasurementDTO> result = measurementService.getAll(pageable, time, time);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(measurementDTO, result.getContent().get(0));
    }

    @Test
    public void getAllTest_FromUser() {
        // Arrange
        Integer pageNumber = 0;
        Integer pageSize = 50;
        Pageable pageable = getPageable(pageNumber, pageSize);
        LocalDateTime time = LocalDateTime.now();
        MeasurementDTO measurementDTO = getMeasurementDTO();
        Mockito.when(measurementRepositoryMock.findRangeFromUser(time, time, 1, pageable)).thenReturn(getMeasurementPage(pageable));
        Mockito.when(conversionServiceMock.convert(Mockito.any(Measurement.class), eq(MeasurementDTO.class))).thenReturn(measurementDTO);

        // Act
        PaginationResponse<MeasurementDTO> result = measurementService.getAll(pageable, time, time, 1);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(measurementDTO, result.getContent().get(0));
    }

    @Test
    public void getByIdTest() {
        // Arrange
        Integer id = 1;
        Mockito.when(measurementRepositoryMock.findById(id)).thenReturn(Optional.of(getMeasurement()));
        Mockito.when(conversionServiceMock.convert(Mockito.any(Measurement.class), eq(MeasurementDTO.class))).thenReturn(getMeasurementDTO());

        // Act
        MeasurementDTO result = measurementService.getById(id);

        // Assert
        Assert.assertNotNull(result);
    }

    private NewMeasurementDTO getNewMeasurementDTO() {
        return new NewMeasurementDTO("Test", 10f, LocalDateTime.now().toString(), "Test");
    }

    private MeasurementDTO getMeasurementDTO() {
        return new MeasurementDTO(1, null, null, 0.5f, LocalDateTime.now(), 47.5f);
    }

    private Measurement getMeasurement() {
        return new Measurement(1, null, null, 0.5f, LocalDateTime.now(), 5f);
    }

    private Page<Measurement> getMeasurementPage(Pageable pageable) {
        List<Measurement> measurements = new ArrayList<>();

        measurements.add(getMeasurement());

        return new PageImpl<>(
                measurements,
                pageable,
                measurements.size()
        );
    }

    private Pageable getPageable(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }

    private ElectricMeter getElectricMeter() {
        return new ElectricMeter(
                1,
                "Test",
                null,
                "Test"
        );
    }

    private Tariff getTariff() {
        return new Tariff(
                1,
                null,
                5f
        );
    }

}
