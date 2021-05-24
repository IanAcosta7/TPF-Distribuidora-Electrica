package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.controller.UserController;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public PostResponse add(User userToAdd) {
        User user = userRepository.save(userToAdd);
        return new PostResponse(
                EntityURLBuilder.buildURL(
                        UserController.PATH,
                        user.getId()
                ),
                HttpStatus.CREATED
        );
    }

    public PaginationResponse<User> get(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return new PaginationResponse<>(userPage.getContent(), userPage.getTotalPages(), userPage.getTotalElements());
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public Integer addTypeToUser(Integer id, Integer typeId) {
        userRepository.setUserType(id, typeId);
        return id;
    }
}
