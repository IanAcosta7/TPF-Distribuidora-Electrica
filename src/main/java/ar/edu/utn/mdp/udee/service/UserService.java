package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import ar.edu.utn.mdp.udee.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    public Integer login(String username, String password) {
        User user = userRepository.getUserByUsernameAndPassword(username, password);
        Integer id = 0;

        if (user != null) {
            id = user.getId();
        }

        return id;
    }

    public Integer add(User user) {
        return userRepository.save(user).getId();
    }

    public Integer addType(UserType userType) {
        return userTypeRepository.save(userType).getId();
    }

    public List<User> get() {
        return userRepository.findAll();
    }

    public List<UserType> getTypes() {
        return userTypeRepository.findAll();
    }

    public Integer addTypeToUser(Integer id, Integer typeId) {
        userRepository.setUserType(id, typeId);
        return id;
    }
}
