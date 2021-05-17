package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.PaginationResponse;
import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import ar.edu.utn.mdp.udee.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public PaginationResponse<User> get(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return new PaginationResponse<>(userPage.getContent(), userPage.getTotalPages(), userPage.getTotalElements());
    }

    public Integer addTypeToUser(Integer id, Integer typeId) {
        userRepository.setUserType(id, typeId);
        return id;
    }
}
