package ar.edu.utn.mdp.udee.converter;

import ar.edu.utn.mdp.udee.model.*;
import ar.edu.utn.mdp.udee.model.DTO.*;
import ar.edu.utn.mdp.udee.model.DTO.Measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.DTO.Measurement.NewMeasurementDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
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
                new ConvertiblePair(TariffTypeDTO.class, TariffType.class),
                // Measurement
                new ConvertiblePair(NewMeasurementDTO.class, Measurement.class),
                new ConvertiblePair(Measurement.class, MeasurementDTO.class),
                //ElectricMeter
                new ConvertiblePair(ElectricMeter.class, ElectricMeterDTO.class),
                new ConvertiblePair(ElectricMeterDTO.class, ElectricMeter.class)
        };
        return Set.of(convertiblePairs);
    }

    @Override
    public Object convert(Object o, TypeDescriptor sourceType, TypeDescriptor targetType) {

        Object result = modelMapper.map(o, targetType.getType());

        if (sourceType.equals(TypeDescriptor.valueOf(NewMeasurementDTO.class)) && targetType.equals(TypeDescriptor.valueOf(Measurement.class))) {
            NewMeasurementDTO input = (NewMeasurementDTO)o;

            Measurement output = new Measurement();
            output.setMeasure(input.getValue());
            output.setMeasureDateTime(LocalDateTime.parse(input.getDate()));

            result = output;
        }

        return result;
    }
}
