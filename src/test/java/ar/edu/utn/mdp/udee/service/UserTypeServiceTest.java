package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.model.dto.user.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.UserTypeRepository;
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
public class UserTypeServiceTest {

    private UserTypeRepository userTypeRepositoryMock;
    private ConversionService conversionServiceMock;
    private UserTypeService userTypeService;

    @BeforeAll
    public void setUp() {
        userTypeRepositoryMock = mock(UserTypeRepository.class);
        conversionServiceMock = mock(ConversionService.class);
        userTypeService = new UserTypeService(userTypeRepositoryMock, conversionServiceMock);
    }

    @Test
    public void addUserTypeTest() {
        // Arrange
        UserType userType = getUserType();
        UserTypeDTO userTypeDTO = getUserTypeDTO();
        Mockito.when(conversionServiceMock.convert(userTypeDTO, UserType.class)).thenReturn(userType);
        Mockito.when(userTypeRepositoryMock.save(userType)).thenReturn(userType);
        Mockito.when(conversionServiceMock.convert(userType, UserTypeDTO.class)).thenReturn(userTypeDTO);

        // Act
        UserTypeDTO result = null;
        try {
            result = userTypeService.addType(userTypeDTO);
        } catch (Exception e) {
            Assertions.fail();
        }

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userTypeDTO, result);
    }

    @Test
    public void getAllTest() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 50;
        Pageable pageable = getPageable(pageNumber, pageSize);
        UserTypeDTO userTypeDTO = getUserTypeDTO();
        Mockito.when(userTypeRepositoryMock.findAll(pageable)).thenReturn(getUserTypePage());
        Mockito.when(conversionServiceMock.convert(Mockito.any(UserType.class), eq(UserTypeDTO.class))).thenReturn(userTypeDTO);

        // Act
        PaginationResponse<UserTypeDTO> result = userTypeService.getTypes(pageNumber, pageSize);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userTypeDTO, result.getContent().get(0));
    }

    @Test
    public void getByIdTest() {
        // Arrange
        Integer id = 1;
        UserTypeDTO userTypeDTO = getUserTypeDTO();
        Mockito.when(userTypeRepositoryMock.findById(id)).thenReturn(Optional.of(getUserType()));
        Mockito.when(conversionServiceMock.convert(Mockito.any(UserType.class), eq(UserTypeDTO.class))).thenReturn(userTypeDTO);

        // Act
        UserTypeDTO result = userTypeService.getById(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userTypeDTO, result);
    }

    @Test
    public void getById_NotFoundTest() {
        // Arrange
        Integer id = 1;
        UserTypeDTO userTypeDTO = getUserTypeDTO();
        Mockito.when(userTypeRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // Act
        UserTypeDTO result = userTypeService.getById(id);

        // Assert
        Assertions.assertNull(result);
    }

    private UserType getUserType() {
        return new UserType(1, "Test");
    }

    private UserTypeDTO getUserTypeDTO() {
        return new UserTypeDTO(1, "Test");
    }

    private List<UserType> getUserTypeList() {
        List<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(getUserType());
        return userTypeList;
    }

    private Page<UserType> getUserTypePage() {
        return new PageImpl<>(getUserTypeList());
    }

    private Pageable getPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}
