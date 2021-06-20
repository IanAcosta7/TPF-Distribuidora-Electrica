package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.range.DateRangeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.MeasurementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class MeasurementControllerTest {

    private MeasurementService measurementServiceMock;
    private MeasurementController measurementController;

    @BeforeAll
    public void setUp() {
        measurementServiceMock = mock(MeasurementService.class);
        measurementController = new MeasurementController(measurementServiceMock);
    }

    @Test
    public void getAllByAddressIdTest() {
        // Arrange
        int addressId = 1, pageNumber = 0, pageSize = 50;
        DateRangeDTO dateRangeDTO = getDateRangeDTO();
        PaginationResponse<MeasurementDTO> response = getMeasurementDTOPaginationResponse();

        Mockito.when(measurementServiceMock.getAllByAddressId(addressId, PageRequest.of(pageNumber, pageSize), LocalDateTime.parse(dateRangeDTO.getSince()), LocalDateTime.parse(dateRangeDTO.getUntil())))
                .thenReturn(response);

        // Act
        ResponseEntity<PaginationResponse<MeasurementDTO>> result = measurementController.getAllByAddressId(addressId, pageNumber, pageSize, dateRangeDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(response, result.getBody());
    }

    private PaginationResponse<MeasurementDTO> getMeasurementDTOPaginationResponse() {
        List<MeasurementDTO> measurementDTOList = new ArrayList<>();
        measurementDTOList.add(getMeasurementDTO());
        return new PaginationResponse<>(
                measurementDTOList,
                1,
                1
        );
    }

    private MeasurementDTO getMeasurementDTO() {
        return new MeasurementDTO(1, 1, 1, 0.5f, LocalDateTime.now(), 0.5f);
    }

    private DateRangeDTO getDateRangeDTO() {
        return new DateRangeDTO("2020-01-01T00:00:00", "2021-01-01T00:00:00");
    }

}
