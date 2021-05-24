package ar.edu.utn.mdp.udee.converter;

import ar.edu.utn.mdp.udee.model.DTO.UserDTO;
import ar.edu.utn.mdp.udee.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOToUserConverter implements Converter<UserDTO, User> {

    private final ModelMapper modelMapper;

    public UserDTOToUserConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User convert(UserDTO source) {
        return modelMapper.map(source, User.class);
    }
}
