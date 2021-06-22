package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.user.UserDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserLoginDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.UserService;
import ar.edu.utn.mdp.udee.service.UserTypeService;
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
public class UserControllerTest {

    private UserService userServiceMock;
    private UserTypeService userTypeServiceMock;
    private UserController userController;

    @BeforeAll
    public void setUp() {
        userServiceMock = mock(UserService.class);
        userTypeServiceMock = mock(UserTypeService.class);
        userController = new UserController(userServiceMock, userTypeServiceMock);
    }

    @Test
    public void loginTest() {
        // Arrange
        String username = "Test";
        String password = "123";
        Mockito.when(userServiceMock.login(username, password)).thenReturn(getUserDTO());

        // Act
        ResponseEntity<UserLoginDTO> result = userController.login("Authorization: " + username + ":" + password);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void login_failedTest() {
        // Arrange
        String username = "Test";
        String password = "123";
        Mockito.when(userServiceMock.login(username, password)).thenReturn(null);

        // Act
        ResponseEntity<UserLoginDTO> result = userController.login("Authorization: " + username + ":" + password);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void addTest() {
        // Arrange
        UserDTO userDTO = getUserDTO();
        Mockito.when(userServiceMock.add(userDTO)).thenReturn(userDTO);

        try (MockedStatic<EntityURLBuilder> entityURLBuilderStaticMock = Mockito.mockStatic(EntityURLBuilder.class)) {
            entityURLBuilderStaticMock.when(() -> EntityURLBuilder.buildURL(
                    Mockito.any(String.class),
                    Mockito.any(Integer.class)
            )).thenReturn("Test");

            // Act
            ResponseEntity<UserDTO> result = userController.add(userDTO);

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
            Assertions.assertEquals(userDTO, result.getBody());
        }
    }

    @Test
    public void getTest() {
        // Arrange
        Integer pageNumber = 0, pageSize = 5;
        PaginationResponse<UserDTO> userDTOPaginationResponse = getUserDTOPaginationResponse();
        Mockito.when(userServiceMock.get(pageNumber, pageSize)).thenReturn(userDTOPaginationResponse);

        // Act
        ResponseEntity<PaginationResponse<UserDTO>> result = userController.get(pageNumber, pageSize);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(userDTOPaginationResponse.getContent(), result.getBody().getContent());
    }

    @Test
    public void getByIdTest() {
        // Arrange
        Integer id = 1;
        UserDTO userDTO = getUserDTO();
        Mockito.when(userServiceMock.getById(id)).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> result = userController.getById(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(userDTO, result.getBody());
    }

    @Test
    public void addTypeTest() {
        // Arrange
        UserTypeDTO userTypeDTO = getUserTypeDTO();
        Mockito.when(userTypeServiceMock.addType(userTypeDTO)).thenReturn(userTypeDTO);

        try (MockedStatic<EntityURLBuilder> entityURLBuilderStaticMock = Mockito.mockStatic(EntityURLBuilder.class)) {
            entityURLBuilderStaticMock.when(() -> EntityURLBuilder.buildURL(
                    Mockito.any(String.class),
                    Mockito.any(Integer.class)
            )).thenReturn("Test");

            // Act
            ResponseEntity<UserTypeDTO> result = userController.addType(userTypeDTO);

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
            Assertions.assertEquals(userTypeDTO, result.getBody());
        }
    }

    @Test
    public void getTypesTest() {
        // Arrange
        Integer pageNumber = 0, pageSize = 5;
        PaginationResponse<UserTypeDTO> userTypeDTOPaginationResponse = getUserTypeDTOPaginationResponse();
        Mockito.when(userTypeServiceMock.getTypes(pageNumber, pageSize)).thenReturn(userTypeDTOPaginationResponse);

        // Act
        ResponseEntity<PaginationResponse<UserTypeDTO>> result = userController.getTypes(pageNumber, pageSize);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(userTypeDTOPaginationResponse.getContent(), result.getBody().getContent());
    }

    @Test
    public void getUserTypeByIdTest() {
        // Arrange
        Integer id = 1;
        UserTypeDTO userTypeDTO = getUserTypeDTO();
        Mockito.when(userTypeServiceMock.getById(id)).thenReturn(userTypeDTO);

        // Act
        ResponseEntity<UserTypeDTO> result = userController.getUserTypeById(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(userTypeDTO, result.getBody());
    }

    @Test
    public void getUserTypeById_NoContentTest() {
        // Arrange
        Integer id = 1;
        UserTypeDTO userTypeDTO = getUserTypeDTO();
        Mockito.when(userTypeServiceMock.getById(id)).thenReturn(null);

        // Act
        ResponseEntity<UserTypeDTO> result = userController.getUserTypeById(id);

        // Assert
        Assertions.assertNull(result.getBody());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    public UserDTO getUserDTO() {
        return new UserDTO(1, getUserTypeDTO(), "user", "password", "Test", "Test");
    }

    public UserTypeDTO getUserTypeDTO() {
        return new UserTypeDTO(1, "Employee");
    }

    private PaginationResponse<UserDTO> getUserDTOPaginationResponse() {
        return new PaginationResponse<UserDTO>(getUserDTOList(), 1, 1);
    }

    private List<UserDTO> getUserDTOList() {
        List<UserDTO> list = new ArrayList<>();
        list.add(getUserDTO());

        return list;
    }

    private PaginationResponse<UserTypeDTO> getUserTypeDTOPaginationResponse() {
        return new PaginationResponse<UserTypeDTO>(getUserTypeDTOList(), 1, 1);
    }

    private List<UserTypeDTO> getUserTypeDTOList() {
        List<UserTypeDTO> list = new ArrayList<>();
        list.add(getUserTypeDTO());

        return list;
    }

}
