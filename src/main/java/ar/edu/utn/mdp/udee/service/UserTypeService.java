package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.repository.UserTypeRepository;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public PostResponse addType(UserType userTypeToAdd) {
        UserType userType = userTypeRepository.save(userTypeToAdd);
        return new PostResponse(EntityURLBuilder.buildURL(UserType.class.getSimpleName(), userType.getId()), HttpStatus.CREATED);
    }

    public List<UserType> getTypes() {
        return userTypeRepository.findAll();
    }

}
