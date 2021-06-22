package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.TariffType;
import ar.edu.utn.mdp.udee.model.dto.tariff.TariffTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.TariffTypeRepository;
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
public class TariffTypeServiceTest {

    private TariffTypeRepository tariffTypeRepositoryMock;
    private ConversionService conversionServiceMock;
    private TariffTypeService tariffTypeService;

    @BeforeAll
    public void setUp() {
        tariffTypeRepositoryMock = mock(TariffTypeRepository.class);
        conversionServiceMock = mock(ConversionService.class);
        tariffTypeService = new TariffTypeService(tariffTypeRepositoryMock, conversionServiceMock);
    }

    @Test
    public void addTariffTypeTest() {
        // Arrange
        TariffType tariffType = getTariffType();
        TariffTypeDTO tariffTypeDTO = getTariffTypeDTO();
        Mockito.when(conversionServiceMock.convert(tariffTypeDTO, TariffType.class)).thenReturn(tariffType);
        Mockito.when(tariffTypeRepositoryMock.save(tariffType)).thenReturn(tariffType);
        Mockito.when(conversionServiceMock.convert(tariffType, TariffTypeDTO.class)).thenReturn(tariffTypeDTO);

        // Act
        TariffTypeDTO result = null;
        try {
            result = tariffTypeService.addTariffType(tariffTypeDTO);
        } catch (Exception e) {
            Assertions.fail();
        }

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(tariffTypeDTO, result);
    }

    @Test
    public void getAllTest() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 50;
        Pageable pageable = getPageable(pageNumber, pageSize);
        TariffTypeDTO tariffTypeDTO = getTariffTypeDTO();
        Mockito.when(tariffTypeRepositoryMock.findAll(pageable)).thenReturn(getTariffTypePage());
        Mockito.when(conversionServiceMock.convert(Mockito.any(TariffType.class), eq(TariffTypeDTO.class))).thenReturn(tariffTypeDTO);

        // Act
        PaginationResponse<TariffTypeDTO> result = tariffTypeService.getTariffTypes(pageNumber, pageSize);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(tariffTypeDTO, result.getContent().get(0));
    }

    @Test
    public void getByIdTest() {
        // Arrange
        Integer id = 1;
        TariffTypeDTO tariffTypeDTO = getTariffTypeDTO();
        Mockito.when(tariffTypeRepositoryMock.findById(id)).thenReturn(Optional.of(getTariffType()));
        Mockito.when(conversionServiceMock.convert(Mockito.any(TariffType.class), eq(TariffTypeDTO.class))).thenReturn(tariffTypeDTO);

        // Act
        TariffTypeDTO result = tariffTypeService.getTariffTypeById(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(tariffTypeDTO, result);
    }

    @Test
    public void getById_NotFoundTest() {
        // Arrange
        Integer id = 1;
        TariffTypeDTO tariffTypeDTO = getTariffTypeDTO();
        Mockito.when(tariffTypeRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // Act
        TariffTypeDTO result = tariffTypeService.getTariffTypeById(id);

        // Assert
        Assertions.assertNull(result);
    }

    private TariffType getTariffType() {
        return new TariffType(1, "Test");
    }

    private TariffTypeDTO getTariffTypeDTO() {
        return new TariffTypeDTO(1, "Test");
    }

    private List<TariffType> getTariffTypeList() {
        List<TariffType> tariffTypeList = new ArrayList<>();
        tariffTypeList.add(getTariffType());
        return tariffTypeList;
    }

    private Page<TariffType> getTariffTypePage() {
        return new PageImpl<>(getTariffTypeList());
    }

    private Pageable getPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}
