package ar.edu.utn.mdp.udee.converter;

import ar.edu.utn.mdp.udee.model.DTO.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserTypeDTOToUserTypeConverter implements Converter<UserTypeDTO, UserType> {

    private final ModelMapper modelMapper;

    public UserTypeDTOToUserTypeConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserType convert(UserTypeDTO source) {
        return modelMapper.map(source, UserType.class);
    }
}
