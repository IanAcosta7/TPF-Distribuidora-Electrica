package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.tariff.TariffDTO;
import ar.edu.utn.mdp.udee.model.dto.tariff.TariffTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.TariffService;
import ar.edu.utn.mdp.udee.service.TariffTypeService;
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
public class TariffControllerTest {

    private TariffController tariffController;
    private TariffService tariffServiceMock;
    private TariffTypeService tariffTypeServiceMock;

    @BeforeAll
    public void setUp() {
        tariffServiceMock = mock(TariffService.class);
        tariffTypeServiceMock = mock(TariffTypeService.class);

        tariffController = new TariffController(tariffServiceMock, tariffTypeServiceMock);
    }

    @Test
    public void getTariffsTest() {
        // Arrange
        Integer pageNumber = 0, pageSize = 5;
        Mockito.when(tariffServiceMock.get(pageNumber, pageSize)).thenReturn(getTariffDTOPaginationResponse());

        // Act
        ResponseEntity<PaginationResponse<TariffDTO>> result = tariffController.getTariffs(pageNumber, pageSize);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void getTariffByIdTest() {
        // Arrange
        Integer id = 1;
        Mockito.when(tariffServiceMock.getTariffById(id)).thenReturn(getTariffDTO());

        // Act
        ResponseEntity<TariffDTO> result = tariffController.getTariffById(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void addTariffTest() {
        // Arrange
        TariffDTO tariffDTO = getTariffDTO();
        Mockito.when(tariffServiceMock.addTariff(tariffDTO)).thenReturn(tariffDTO);

        try (MockedStatic<EntityURLBuilder> entityURLBuilderStaticMock = Mockito.mockStatic(EntityURLBuilder.class)) {
            entityURLBuilderStaticMock.when(() -> EntityURLBuilder.buildURL(
                    Mockito.any(String.class),
                    Mockito.any(Integer.class)
            )).thenReturn("Test");

            // Act
            ResponseEntity<TariffDTO> result = tariffController.addTariff(tariffDTO);

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        }
    }

    @Test
    public void deleteTariffTest() {
        // Arrange
        Integer id = 1;

        // Act
        ResponseEntity<Integer> result = tariffController.delete(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void getTariffTypesTest() {
        // Arrange
        Integer pageNumber = 0, pageSize = 5;
        Mockito.when(tariffTypeServiceMock.getTariffTypes(pageNumber, pageSize)).thenReturn(getTariffTypeDTOPaginationResponse());

        // Act
        ResponseEntity<PaginationResponse<TariffTypeDTO>> result = tariffController.getTariffTypes(pageNumber, pageSize);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void getTariffTypeByIdTest() {
        // Arrange
        Integer id = 1;
        Mockito.when(tariffTypeServiceMock.getTariffTypeById(id)).thenReturn(getTariffTypeDTO());

        // Act
        ResponseEntity<TariffTypeDTO> result = tariffController.getTariffTypeById(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void addTariffTypeTest() {
        // Arrange
        TariffTypeDTO tariffTypeDTO = getTariffTypeDTO();
        Mockito.when(tariffTypeServiceMock.addTariffType(tariffTypeDTO)).thenReturn(tariffTypeDTO);

        try (MockedStatic<EntityURLBuilder> entityURLBuilderStaticMock = Mockito.mockStatic(EntityURLBuilder.class)) {
            entityURLBuilderStaticMock.when(() -> EntityURLBuilder.buildURL(
                    Mockito.any(String.class),
                    Mockito.any(Integer.class)
            )).thenReturn("Test");

            // Act
            ResponseEntity<TariffTypeDTO> result = tariffController.addTariffType(tariffTypeDTO);

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        }
    }

    private PaginationResponse<TariffDTO> getTariffDTOPaginationResponse() {
        return new PaginationResponse<TariffDTO>(getTariffDTOList(), 1, 1);
    }

    private PaginationResponse<TariffTypeDTO> getTariffTypeDTOPaginationResponse() {
        return new PaginationResponse<>(getTariffTypeDTOList(), 1, 1);
    }

    private List<TariffTypeDTO> getTariffTypeDTOList() {
        List<TariffTypeDTO> tariffTypeDTOList = new ArrayList<>();
        tariffTypeDTOList.add(getTariffTypeDTO());

        return tariffTypeDTOList;
    }

    private List<TariffDTO> getTariffDTOList() {
        List<TariffDTO> tariffDTOList = new ArrayList();
        tariffDTOList.add(getTariffDTO());

        return tariffDTOList;
    }

    private TariffDTO getTariffDTO() {
        return new TariffDTO(1, getTariffTypeDTO(), 0.5f);
    }

    private TariffTypeDTO getTariffTypeDTO() {
        return new TariffTypeDTO(1, "Test");
    }

}
