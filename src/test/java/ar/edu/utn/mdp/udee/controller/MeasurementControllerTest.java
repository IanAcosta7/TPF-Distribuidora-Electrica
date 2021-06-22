package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.bill.BillDTO;
import ar.edu.utn.mdp.udee.model.dto.consumption.ConsumptionDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.NewMeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.range.DateRangeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.MeasurementService;
import ar.edu.utn.mdp.udee.util.EntityURLBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.util.CollectionUtils;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.core.CollectionFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.security.auth.login.LoginException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

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
    public void addMeasurementTest() throws LoginException {
        // Arrange
        NewMeasurementDTO newMeasurementDTO = getNewMeasurementDTO();
        MeasurementDTO measurementDTO = getMeasurementDTO();
        Mockito.when(measurementServiceMock.addMeasurement(newMeasurementDTO)).thenReturn(measurementDTO);

        try (MockedStatic<EntityURLBuilder> entityURLBuilderStaticMock = Mockito.mockStatic(EntityURLBuilder.class)) {
            entityURLBuilderStaticMock.when(() -> EntityURLBuilder.buildURL(
                    Mockito.any(String.class),
                    Mockito.any(Integer.class)
            )).thenReturn("Test");

            // Act
            ResponseEntity<MeasurementDTO> result = null;
            try {
                result = measurementController.add(newMeasurementDTO);
            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        }
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

    @Test
    public void getAll_EmployeeTest() {
        // Arrange
        int pageNumber = 0, pageSize = 5;
        DateRangeDTO dateRangeDTO = getDateRangeDTO();
        PaginationResponse<MeasurementDTO> measurementDTOPaginationResponse = getMeasurementDTOPaginationResponse();
        Mockito.when(measurementServiceMock.getAll(PageRequest.of(pageNumber, pageSize), LocalDateTime.parse(dateRangeDTO.getSince()), LocalDateTime.parse(dateRangeDTO.getUntil()))).thenReturn(measurementDTOPaginationResponse);

        Authentication authMock = mock(Authentication.class);
        List list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        Mockito.when(authMock.getAuthorities()).thenReturn(list);

        // Act
        ResponseEntity<PaginationResponse<MeasurementDTO>> result = null;
        try {
            result = measurementController.getAll(authMock, pageNumber, pageSize, dateRangeDTO);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(measurementDTOPaginationResponse.getContent(), result.getBody().getContent());
    }

    @Test
    public void getAll_ClientTest() {
        // Arrange
        int id = 1, pageNumber = 0, pageSize = 5;
        DateRangeDTO dateRangeDTO = getDateRangeDTO();
        PaginationResponse<MeasurementDTO> measurementDTOPaginationResponse = getMeasurementDTOPaginationResponse();

        Authentication authMock = mock(Authentication.class);
        List list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        Mockito.when(authMock.getAuthorities()).thenReturn(list);
        Mockito.when(authMock.getPrincipal()).thenReturn(id);
        Mockito.when(measurementServiceMock.getAll(PageRequest.of(pageNumber, pageSize), LocalDateTime.parse(dateRangeDTO.getSince()), LocalDateTime.parse(dateRangeDTO.getUntil()), id)).thenReturn(measurementDTOPaginationResponse);

        // Act
        ResponseEntity<PaginationResponse<MeasurementDTO>> result = null;
        try {
            result = measurementController.getAll(authMock, pageNumber, pageSize, dateRangeDTO);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(measurementDTOPaginationResponse.getContent(), result.getBody().getContent());
    }

    @Test
    public void getConsumptionTest() {
        // Arrange
        Integer id = 1;
        Authentication auth = new TestingAuthenticationToken(id, null);
        DateRangeDTO dateRangeDTO = getDateRangeDTO();
        ConsumptionDTO consumptionDTO = getConsumptionDTO();

        Mockito.when(measurementServiceMock.getConsumption(id, LocalDateTime.parse(dateRangeDTO.getSince()), LocalDateTime.parse(dateRangeDTO.getUntil()))).thenReturn(consumptionDTO);

        // Act
        ResponseEntity<ConsumptionDTO> result = measurementController.getConsumption(auth, dateRangeDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(consumptionDTO, result.getBody());
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

    private NewMeasurementDTO getNewMeasurementDTO() {
        return new NewMeasurementDTO("Test", 0.5f, "2020-01-01T00:00:00", "Test");
    }

    private DateRangeDTO getDateRangeDTO() {
        return new DateRangeDTO("2020-01-01T00:00:00", "2021-01-01T00:00:00");
    }

    private ConsumptionDTO getConsumptionDTO() {
        return new ConsumptionDTO(0f, 5f);
    }

}
