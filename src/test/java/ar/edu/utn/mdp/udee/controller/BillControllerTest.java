package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.bill.BillDTO;
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

}
