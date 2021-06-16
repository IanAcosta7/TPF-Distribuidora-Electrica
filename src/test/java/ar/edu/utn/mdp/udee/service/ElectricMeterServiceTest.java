package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.meter.ElectricMeterDTO;
import ar.edu.utn.mdp.udee.model.ElectricMeter;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.ElectricMeterRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class ElectricMeterServiceTest {

    private ElectricMeterService electricMeterService;
    private ElectricMeterRepository electricMeterRepositoryMock;
    private ConversionService conversionServiceMock;

    @BeforeAll
    public void setUp() {
        electricMeterRepositoryMock = mock(ElectricMeterRepository.class);
        conversionServiceMock = mock(ConversionService.class);

        electricMeterService = new ElectricMeterService(electricMeterRepositoryMock, conversionServiceMock);
    }

    @Test
    public void addElectricMeterTest() {
        // Arrange
        Mockito.when(conversionServiceMock.convert(Mockito.any(ElectricMeterDTO.class), eq(ElectricMeter.class))).thenReturn(getElectricMeter());
        Mockito.when(electricMeterRepositoryMock.save(Mockito.any(ElectricMeter.class))).thenReturn(getElectricMeter());
        Mockito.when(conversionServiceMock.convert(Mockito.any(ElectricMeter.class), eq(ElectricMeterDTO.class))).thenReturn(getElectricMeterDTO());

        // Act
        ElectricMeterDTO result = electricMeterService.addElectricMeter(getElectricMeterDTO());

        // Assert
        Assert.assertNotNull(result);
    }

    @Test
    public void deleteTariffs_Test() {
        // Arrange
        Integer id = 1;

        // Act
        Integer result = electricMeterService.delete(id);

        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    public void getAllTest() {
        // Arrange
        Integer pageNumber = 0;
        Integer pageSize = 50;
        Mockito.when(electricMeterRepositoryMock.findAll(getPageable(pageNumber, pageSize))).thenReturn((getElectricMeterPage(getPageable(pageNumber, pageSize))));
        Mockito.when(conversionServiceMock.convert(Mockito.any(ElectricMeter.class), eq(ElectricMeterDTO.class))).thenReturn(getElectricMeterDTO());

        // Act
        PaginationResponse<ElectricMeterDTO> result = electricMeterService.getAll(pageNumber, pageSize);

        // Assert
        Assert.assertNotNull(result);
    }

    @Test
    public void getByIdTest() {
        // Arrange
        Integer id = 1;
        Mockito.when(electricMeterRepositoryMock.findById(id)).thenReturn(Optional.of(getElectricMeter()));
        Mockito.when(conversionServiceMock.convert(Mockito.any(ElectricMeter.class), eq(ElectricMeterDTO.class))).thenReturn(getElectricMeterDTO());

        // Act
        ElectricMeterDTO result = electricMeterService.getById(id);

        // Assert
        Assert.assertNotNull(result);
    }

    private ElectricMeterDTO getElectricMeterDTO() {
        return new ElectricMeterDTO(1, "Test", null);
    }

    private ElectricMeter getElectricMeter() {
        return new ElectricMeter(1, "Test", null, "Test");
    }

    private Page<ElectricMeter> getElectricMeterPage(Pageable pageable) {
        List<ElectricMeter> electricMeters = new ArrayList<>();

        electricMeters.add(getElectricMeter());

        return new PageImpl<>(
                electricMeters,
                pageable,
                electricMeters.size()
        );
    }

    private Pageable getPageable(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}
