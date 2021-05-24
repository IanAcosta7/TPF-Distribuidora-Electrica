package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.DTO.UserDTO;
import ar.edu.utn.mdp.udee.model.DTO.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.PaginationResponse;
import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

public class UserServiceTest {

    UserRepository userRepositoryMock;
    ConversionService conversionServiceMock;
    UserService userService;

    @Before
    public void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        conversionServiceMock = mock(ConversionService.class);
        userService = new UserService(userRepositoryMock, conversionServiceMock);
    }

    @Test
    public void loginTest() {
        // Arrange
        User user = getUser();
        Mockito.when(userRepositoryMock.getUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

        // Act
        Integer result = userService.login(user.getUsername(), user.getPassword());

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(user.getId(), result);
    }

    @Test
    public void addTest() {
        // Arrange
        Integer id = 1;
        User user = getUserIdNull();
        User userReturned = getUser();
        UserDTO userDTO = getUserDTOIdNull();
        Mockito.when(userRepositoryMock.save(user)).thenReturn(userReturned);

        // Act
        Integer result = userService.add(userDTO);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(id, result);
    }

    @Test
    public void getTest() {
        // Arrange
        Integer page = 0;
        Integer size = 1;
        Pageable pageable = PageRequest.of(page, size);
        List<UserDTO> content = new ArrayList<>();
        content.add(getUserDTO());
        Page userPage = new PageImpl(content, pageable, size);
        Mockito.when(userRepositoryMock.findAll(pageable)).thenReturn(userPage);

        // Act
        PaginationResponse<UserDTO> result = userService.get(page, size);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(content, result.getContent());
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

    public User getUserIdNull() {
        return new User(null, null, "user", "password", "Test", "Test");
    }

    private UserDTO getUserDTOIdNull() {
        return new UserDTO(null, null, "Test", "Test", "Test", "Test");
    }
}
