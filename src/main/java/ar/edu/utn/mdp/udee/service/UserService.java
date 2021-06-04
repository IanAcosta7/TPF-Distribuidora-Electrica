package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.dto.UserDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;

    @Autowired
    public UserService(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    public Integer login(String username, String password) {
        User user = userRepository.getUserByUsernameAndPassword(username, password);
        Integer id = 0;

        if (user != null) {
            id = user.getId();
        }

        return id;
    }

    public UserDTO add(UserDTO userDTO) {
        User user = userRepository.save(conversionService.convert(userDTO, User.class));
        return conversionService.convert(user, UserDTO.class);
    }

    public PaginationResponse<UserDTO> get(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserDTO> userDTOPage = userPage.map(user -> conversionService.convert(user, UserDTO.class));
        return new PaginationResponse<>(userDTOPage.getContent(), userDTOPage.getTotalPages(), userDTOPage.getTotalElements());
    }

    public UserDTO getById(Integer id) {
        return conversionService.convert(userRepository.findById(id).orElse(null), UserDTO.class);
    }

    public Integer addTypeToUser(Integer id, Integer typeId) {
        userRepository.setUserType(id, typeId);
        return id;
    }
}
