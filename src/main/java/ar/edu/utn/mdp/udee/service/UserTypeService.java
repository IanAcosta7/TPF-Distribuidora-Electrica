package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.repository.UserTypeRepository;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public PaginationResponse<UserType> getTypes(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserType> userTypePage = userTypeRepository.findAll(pageable);
        return new PaginationResponse<>(userTypePage.getContent(), userTypePage.getTotalPages(), userTypePage.getTotalElements());
    }

}
