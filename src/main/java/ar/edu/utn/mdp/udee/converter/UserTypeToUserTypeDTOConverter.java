package ar.edu.utn.mdp.udee.converter;

import ar.edu.utn.mdp.udee.model.DTO.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserTypeToUserTypeDTOConverter implements Converter<UserType, UserTypeDTO> {

    private final ModelMapper modelMapper;

    public UserTypeToUserTypeDTOConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserTypeDTO convert(UserType source) {
        return modelMapper.map(source, UserTypeDTO.class);
    }
}
