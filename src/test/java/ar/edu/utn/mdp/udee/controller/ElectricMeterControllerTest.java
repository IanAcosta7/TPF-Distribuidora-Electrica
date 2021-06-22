package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.meter.ElectricMeterDTO;
import ar.edu.utn.mdp.udee.model.dto.meter.MeterBrandDTO;
import ar.edu.utn.mdp.udee.model.dto.meter.MeterModelDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.ElectricMeterService;
import ar.edu.utn.mdp.udee.util.EntityURLBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class ElectricMeterControllerTest {

    private ElectricMeterService electricMeterServiceMock;
    private ElectricMeterController electricMeterController;

    @BeforeAll
    public void setUp() {
        electricMeterServiceMock = mock(ElectricMeterService.class);
        electricMeterController = new ElectricMeterController(electricMeterServiceMock);
    }

    @Test
    public void addTest() {
        // Arrange
        ElectricMeterDTO electricMeterDTO = getElectricMeterDTO();
        Mockito.when(electricMeterServiceMock.addElectricMeter(electricMeterDTO)).thenReturn(electricMeterDTO);

        try (MockedStatic<EntityURLBuilder> entityURLBuilderStaticMock = Mockito.mockStatic(EntityURLBuilder.class)) {
            entityURLBuilderStaticMock.when(() -> EntityURLBuilder.buildURL(
                    Mockito.any(String.class),
                    Mockito.any(Integer.class)
            )).thenReturn("Test");

            // Act
            ResponseEntity<ElectricMeterDTO> result = electricMeterController.add(electricMeterDTO);

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
            Assertions.assertEquals(electricMeterDTO, result.getBody());
        }
    }

    @Test
    public void deleteTest() {
        // Arrange
        Integer id = 1;

        // Act
        ResponseEntity<Integer> result = electricMeterController.delete(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void getAllTest() {
        // Arrange
        Integer pageNumber = 0, pageSize = 5;
        PaginationResponse<ElectricMeterDTO> electricMeterDTOPaginationResponse = getElectricMeterDTOPaginationResponse();
        Mockito.when(electricMeterServiceMock.getAll(pageNumber, pageSize)).thenReturn(electricMeterDTOPaginationResponse);

        // Act
        ResponseEntity<PaginationResponse<ElectricMeterDTO>> result = electricMeterController.getAll(pageNumber, pageSize);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(electricMeterDTOPaginationResponse.getContent(), result.getBody().getContent());
    }

    @Test
    public void getByIdTest() {
        // Arrange
        Integer id = 1;
        ElectricMeterDTO electricMeterDTO = getElectricMeterDTO();
        Mockito.when(electricMeterServiceMock.getById(id)).thenReturn(electricMeterDTO);

        // Act
        ResponseEntity<ElectricMeterDTO> result = electricMeterController.getById(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(electricMeterDTO, result.getBody());
    }

    private ElectricMeterDTO getElectricMeterDTO() {
        return new ElectricMeterDTO(1, "Test", new MeterModelDTO(1, new MeterBrandDTO(1, "Test"), "Test"));
    }

    private PaginationResponse<ElectricMeterDTO> getElectricMeterDTOPaginationResponse() {
        return new PaginationResponse<>(getElectricMeterDTOList(), 1, 1);
    }

    private List<ElectricMeterDTO> getElectricMeterDTOList() {
        List<ElectricMeterDTO> list = new ArrayList<>();
        list.add(getElectricMeterDTO());

        return list;
    }

}
