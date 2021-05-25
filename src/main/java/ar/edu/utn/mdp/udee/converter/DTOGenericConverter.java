package ar.edu.utn.mdp.udee.converter;

import ar.edu.utn.mdp.udee.model.DTO.TariffDTO;
import ar.edu.utn.mdp.udee.model.DTO.TariffTypeDTO;
import ar.edu.utn.mdp.udee.model.DTO.UserDTO;
import ar.edu.utn.mdp.udee.model.DTO.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.model.TariffType;
import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DTOGenericConverter implements GenericConverter {

    private ModelMapper modelMapper;

    public DTOGenericConverter (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        ConvertiblePair[] convertiblePairs = new ConvertiblePair[] {
                // User
                new ConvertiblePair(User.class, UserDTO.class),
                new ConvertiblePair(UserDTO.class, User.class),
                new ConvertiblePair(UserType.class, UserTypeDTO.class),
                new ConvertiblePair(UserTypeDTO.class, UserType.class),
                // Tariff
                new ConvertiblePair(Tariff.class, TariffDTO.class),
                new ConvertiblePair(TariffDTO.class, Tariff.class),
                new ConvertiblePair(TariffType.class, TariffTypeDTO.class),
                new ConvertiblePair(TariffTypeDTO.class, TariffType.class)
        };
        return Set.of(convertiblePairs);
    }

    @Override
    public Object convert(Object o, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return modelMapper.map(o, targetType.getType());
    }
}
