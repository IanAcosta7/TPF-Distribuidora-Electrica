package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public Integer addType(UserType userType) {
        return userTypeRepository.save(userType).getId();
    }

    public List<UserType> getTypes() {
        return userTypeRepository.findAll();
    }

}
