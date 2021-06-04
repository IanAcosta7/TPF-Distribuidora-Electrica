package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.TariffDTO;
import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.TariffRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class TariffServiceTest {

    private TariffService tariffService;
    @Mock
    private TariffRepository tariffRepositoryMock;
    @Mock
    private ConversionService conversionServiceMock;

    @BeforeAll
    public void setUp() {
        tariffRepositoryMock = mock(TariffRepository.class);
        conversionServiceMock = mock(ConversionService.class);
        tariffService = new TariffService(tariffRepositoryMock, conversionServiceMock);
    }

    @Test
    public void getTariffs_Test() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 1;
        Pageable pageable = getPageable(pageNumber, pageSize);
        Page<Tariff> tariffPage = getTariffPage(pageable);
        Mockito.when(tariffRepositoryMock.findAll(pageable)).thenReturn(tariffPage);

        // Act
        PaginationResponse<TariffDTO> result = tariffService.get(pageNumber, pageSize);

        // Assert
        Assert.assertNotNull(result);
    }

    private Tariff getTariff() {
        return new Tariff(1, null, 1.5f);
    }

    private Page<Tariff> getTariffPage(Pageable pageable) {
        List<Tariff> content = new ArrayList<>();

        content.add(getTariff());

        return new PageImpl<>(content, pageable, pageable.getPageSize());
    }

    private Pageable getPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}
