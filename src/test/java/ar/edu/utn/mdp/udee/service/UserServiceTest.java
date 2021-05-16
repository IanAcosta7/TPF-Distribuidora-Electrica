package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class UserServiceTest {

    UserRepository userRepository;
    UserService userService;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void loginTest() {
        // Arrange
        User user = getUser();
        Mockito.when(userRepository.getUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

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
        User user = new User(id, null, "Test", "Test", "Test", "Test");
        User userReturned = getUser();
        Mockito.when(userRepository.save(user)).thenReturn(userReturned);

        // Act
        Integer result = userService.add(user);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(id, result);
    }

    @Test
    public void getTest() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(getUser());
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.get();

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(userList, result);
        Assert.assertEquals(userList.size(), result.size());
    }

    @Test
    public void addTypeToUserTest() {
        // Arrange
        int id = 1;
        int typeId = 2;
        Mockito.when(userRepository.setUserType(id, typeId)).thenReturn(id);

        // Act
        int result = userService.addTypeToUser(id, typeId);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(id, result);
    }

    public User getUser() {
        return new User(1, null, "user", "password", "Test", "Test");
    }
}
