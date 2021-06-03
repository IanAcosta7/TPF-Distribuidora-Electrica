package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.DTO.Measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.DTO.Measurement.NewMeasurementDTO;
import ar.edu.utn.mdp.udee.model.Measurement;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.MeasurementRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
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

    @BeforeAll
    public void setUp() {
        measurementRepositoryMock = mock(MeasurementRepository.class);
        conversionServiceMock = mock(ConversionService.class);

        measurementService = new MeasurementService(measurementRepositoryMock, conversionServiceMock);
    }

    @Test
    public void addMeasurementTest() {
        // Arrange
        Mockito.when(conversionServiceMock.convert(Mockito.any(NewMeasurementDTO.class), eq(Measurement.class))).thenReturn(getMeasurement());
        Mockito.when(measurementRepositoryMock.save(Mockito.any(Measurement.class))).thenReturn(getMeasurement());
        Mockito.when(conversionServiceMock.convert(Mockito.any(Measurement.class), eq(MeasurementDTO.class))).thenReturn(getMeasurementDTO());

        // Act
        MeasurementDTO result = measurementService.addMeasurement(getNewMeasurementDTO());

        // Assert
        Assert.assertNotNull(result);
    }

    @Test
    public void getAllTest() {
        // Arrange
        Integer pageNumber = 0;
        Integer pageSize = 50;
        Mockito.when(measurementRepositoryMock.findAll(getPageable(pageNumber, pageSize))).thenReturn((getMeasurementPage(getPageable(pageNumber, pageSize))));
        Mockito.when(conversionServiceMock.convert(Mockito.any(Measurement.class), eq(MeasurementDTO.class))).thenReturn(getMeasurementDTO());

        // Act
        PaginationResponse<MeasurementDTO> result = measurementService.getAll(pageNumber, pageSize);

        // Assert
        Assert.assertNotNull(result);
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
        return new NewMeasurementDTO("Test", 0.5f, LocalDateTime.now().toString(), "Test");
    }

    private MeasurementDTO getMeasurementDTO() {
        return new MeasurementDTO(1, null, null, 0.5f, LocalDateTime.now());
    }

    private Measurement getMeasurement() {
        return new Measurement(1, null, null, 0.5f, LocalDateTime.now());
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

}
