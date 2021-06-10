package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.user.UserDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserLoginDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import org.junit.Assert;
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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    UserRepository userRepositoryMock;
    ConversionService conversionServiceMock;
    UserService userService;

    @BeforeAll
    public void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        conversionServiceMock = mock(ConversionService.class);
        userService = new UserService(userRepositoryMock, conversionServiceMock);
    }

    @Test
    public void login_UserExistsTest() {
        // Arrange
        User user = getUser();
        Mockito.when(userRepositoryMock.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

        // Act
        UserDTO result = userService.login(user.getUsername(), user.getPassword());

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void login_UserNotExistsTest() {
        // Arrange
        User user = getUser();
        Mockito.when(userRepositoryMock.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

        // Act
        UserDTO result = userService.login("Test", "Test");

        // Assert
        Assertions.assertNull(result);
    }

    @Test
    public void addTest() {
        // Arrange
        User user = getUserIdNull();
        User userReturned = getUser();
        UserDTO userDTO = getUserDTOIdNull();
        Mockito.when(userRepositoryMock.save(user)).thenReturn(userReturned);
        Mockito.when(conversionServiceMock.convert(Mockito.any(UserDTO.class), eq(User.class))).thenReturn(getUser());
        Mockito.when(conversionServiceMock.convert(Mockito.any(User.class), eq(UserDTO.class))).thenReturn(getUserDTO());

        // Act
        UserDTO result = userService.add(userDTO);

        // Assert
        Assert.assertNotNull(result);
    }

    @Test
    public void getTest() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 1;
        Pageable pageable = getPageable(pageNumber, pageSize);
        Page<User> userPage = getUserPage(pageable);
        Mockito.when(conversionServiceMock.convert(Mockito.any(User.class), eq(UserDTO.class))).thenReturn(getUserDTO());
        Mockito.when(userRepositoryMock.findAll(pageable)).thenReturn(userPage);

        // Act
        PaginationResponse<UserDTO> result = userService.get(pageNumber, pageSize);

        // Assert
        Assert.assertNotNull(result);
    }

    @Test
    public void addTypeToUserTest() {
        // Arrange
        int id = 1;
        int typeId = 2;
        Mockito.when(userRepositoryMock.setUserType(id, typeId)).thenReturn(id);

        // Act
        int result = userService.addTypeToUser(id, typeId);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(id, result);
    }

    public UserDTO getUserDTO() {
        return new UserDTO(1, getUserTypeDTO(), "user", "password", "Test", "Test");
    }

    public UserTypeDTO getUserTypeDTO() {
        return new UserTypeDTO(1, "Employee");
    }

    public User getUser() {
        return new User(1, null, "user", "password", "Test", "Test");
    }

    public Page<User> getUserPage(Pageable pageable) {
        List<User> content = new ArrayList<>();

        content.add(getUser());

        return new PageImpl(content, pageable, pageable.getPageSize());
    }

    public Pageable getPageable(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }

    public User getUserIdNull() {
        return new User(null, null, "user", "password", "Test", "Test");
    }

    private UserDTO getUserDTOIdNull() {
        return new UserDTO(null, null, "Test", "Test", "Test", "Test");
    }
}
