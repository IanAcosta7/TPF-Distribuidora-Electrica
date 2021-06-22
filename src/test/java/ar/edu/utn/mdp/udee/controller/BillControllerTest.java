package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.bill.BillDTO;
import ar.edu.utn.mdp.udee.model.dto.meter.ElectricMeterDTO;
import ar.edu.utn.mdp.udee.model.dto.range.DateRangeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.BillService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class BillControllerTest {

    private BillService billServiceMock;
    private BillController billController;

    @BeforeAll
    public void setUp() {
        billServiceMock = mock(BillService.class);

        billController = new BillController(billServiceMock);
    }

    @Test
    public void getAllTest() {
        // Arrange
        int pageNumber = 0, pageSize = 5;
        DateRangeDTO dateRangeDTO = getDateRangeDTO();
        PaginationResponse<BillDTO> billDTOPaginationResponse = getBillDTOPaginationResponse();
        Mockito.when(billServiceMock.getAll(PageRequest.of(pageNumber, pageSize), Date.valueOf(dateRangeDTO.getSince()), Date.valueOf(dateRangeDTO.getUntil()))).thenReturn(billDTOPaginationResponse);

        // Act
        ResponseEntity<PaginationResponse<BillDTO>> result = billController.getAll(pageNumber, pageSize, dateRangeDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(billDTOPaginationResponse.getContent(), result.getBody().getContent());
    }

    @Test
    public void getUnpaidByAddress() {
        // Arrange
        Integer addressId = 1, pageNumber = 0, pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        PaginationResponse<BillDTO> billDTOPaginationResponse = getBillDTOPaginationResponse();
        Mockito.when(billServiceMock.getUnpaidByAddress(pageable, addressId)).thenReturn(billDTOPaginationResponse);

        // Act
        ResponseEntity<PaginationResponse<BillDTO>> result = billController.getUnpaidByAddress(pageNumber, pageSize, addressId);
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(billDTOPaginationResponse , result.getBody());
    }

    @Test
    public void getUnpaidByClient() {
        // Arrange
        // Act
        // Assert
    }

    private PaginationResponse<BillDTO> getBillDTOPaginationResponse() {
        Page<BillDTO> billDTOPage = getBillDTOPage();

        return new PaginationResponse<BillDTO>(billDTOPage.getContent(), billDTOPage.getTotalPages(), billDTOPage.getTotalElements());
    }

    private Page<BillDTO> getBillDTOPage() {
        List<BillDTO> billDTOS = new ArrayList<>();

        billDTOS.add(getBillDTO());

        return new PageImpl<>(billDTOS);
    }

    private BillDTO getBillDTO() {
        return new BillDTO(1, null, null, null, new Date(0), 1f, 1f, 1f);
    }

    private DateRangeDTO getDateRangeDTO() {
        return new DateRangeDTO("2020-01-01", "2021-01-01");
    }

}
