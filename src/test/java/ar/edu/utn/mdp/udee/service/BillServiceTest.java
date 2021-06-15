package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Bill;
import ar.edu.utn.mdp.udee.model.dto.bill.BillDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.BillRepository;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class BillServiceTest {

    private BillRepository billRepositoryMock;
    private ConversionService conversionServiceMock;
    private BillService billService;

    @BeforeAll
    public void setUp() {
        billRepositoryMock = mock(BillRepository.class);
        conversionServiceMock = mock(ConversionService.class);

        billService = new BillService(billRepositoryMock, conversionServiceMock);
    }

    @Test
    public void getAllTest() {
        // Arrange
        int pageNumber = 0, pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Date sinceOrUntil = new Date(0);
        Page<Bill> billPage = getBillPage();

        Mockito.when(billRepositoryMock.findByRange(sinceOrUntil, sinceOrUntil, pageable)).thenReturn(billPage);
        Mockito.when(conversionServiceMock.convert(getBill(), BillDTO.class)).thenReturn(getBillDTO());

        // Act
        PaginationResponse<BillDTO> result = billService.getAll(pageable, sinceOrUntil, sinceOrUntil);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(getBillDTO(), result.getContent().get(0));
    }

    @Test
    public void getUnpaidByAddress() {
        // Arrange
        int addressId = 1, pageNumber = 0, pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Bill> billPage = getBillPage();
        Bill bill = getBill();
        BillDTO billDTO = getBillDTO();
        Mockito.when(billRepositoryMock.findUnpaidByAddress(addressId, pageable)).thenReturn(billPage);
        Mockito.when(conversionServiceMock.convert(bill, BillDTO.class)).thenReturn(billDTO);

        // Act
        PaginationResponse<BillDTO> result = billService.getUnpaidByAddress(pageable, addressId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(billDTO, result.getContent().get(0));
    }

    @Test
    public void getUnpaidByClient() {
        // Arrange
        int clientId = 1, pageNumber = 0, pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Bill> billPage = getBillPage();
        Bill bill = getBill();
        BillDTO billDTO = getBillDTO();
        Mockito.when(billRepositoryMock.findUnpaidByClient(clientId, pageable)).thenReturn(billPage);
        Mockito.when(conversionServiceMock.convert(bill, BillDTO.class)).thenReturn(billDTO);

        // Act
        PaginationResponse<BillDTO> result = billService.getUnpaidByClient(pageable, clientId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(billDTO, result.getContent().get(0));
    }

    private Page<Bill> getBillPage() {
        List<Bill> bills = new ArrayList<>();

        bills.add(getBill());

        return new PageImpl<>(bills);
    }

    private Bill getBill() {
        return new Bill(1, null, null, null, new Date(0), 1f, 1f, 1f);
    }

    private BillDTO getBillDTO() {
        return new BillDTO(1, null, null, null, new Date(0), 1f, 1f, 1f);
    }

}
