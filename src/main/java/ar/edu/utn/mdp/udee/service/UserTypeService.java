package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.controller.UserController;
import ar.edu.utn.mdp.udee.model.DTO.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.repository.UserTypeRepository;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;
    private final ConversionService conversionService;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository, ConversionService conversionService) {
        this.userTypeRepository = userTypeRepository;
        this.conversionService = conversionService;
    }

    public UserTypeDTO addType(UserTypeDTO userTypeToDTO) {
        UserType userType = userTypeRepository.save(conversionService.convert(userTypeToDTO, UserType.class));
        return conversionService.convert(userType, UserTypeDTO.class);
    }

    public PaginationResponse<UserTypeDTO> getTypes(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserType> userTypePage = userTypeRepository.findAll(pageable);
        Page<UserTypeDTO> userTypeDTOPage = userTypePage.map(userType -> conversionService.convert(userType, UserTypeDTO.class));
        return new PaginationResponse<>(
                userTypeDTOPage.getContent(),
                userTypeDTOPage.getTotalPages(),
                userTypeDTOPage.getTotalElements()
        );
    }

}
