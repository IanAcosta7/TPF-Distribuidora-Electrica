package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class UserServiceTest {

    UserRepository userRepositoryMock;
    UserService userService;

    @Before
    public void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        userService = new UserService(userRepositoryMock);
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
        String responseURL = "testurl";
        Integer id = 1;
        User user = new User(id, null, "Test", "Test", "Test", "Test");
        User userReturned = getUser();
        MockedStatic<EntityURLBuilder> entityURLBuilderStaticMock = Mockito.mockStatic(EntityURLBuilder.class);
        Mockito.when(userRepositoryMock.save(user)).thenReturn(userReturned);
        entityURLBuilderStaticMock.when(() -> EntityURLBuilder.buildURL(User.class.getSimpleName(), id)).thenReturn(responseURL);

        // Act
        PostResponse result = userService.add(user);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.CREATED, result.getStatus());
    }

    @Test
    public void getTest() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 1;
        Pageable pageable = getPageable(pageNumber, pageSize);
        Page<User> userPage = getUserPage(pageable);
        Mockito.when(userRepositoryMock.findAll(pageable)).thenReturn(userPage);

        // Act
        PaginationResponse<User> result = userService.get(pageNumber, pageSize);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(userPage.getContent(), result.getContent());
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

    public User getUser() {
        return new User(1, null, "user", "password", "Test", "Test");
    }

    public Page<User> getUserPage(Pageable pageable) {
        List<User> content = new ArrayList<>();

        content.add(getUser());

        return new PageImpl<>(content, pageable, pageable.getPageSize());
    }

    public Pageable getPageable(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}
