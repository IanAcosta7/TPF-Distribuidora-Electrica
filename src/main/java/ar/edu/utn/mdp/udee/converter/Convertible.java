package ar.edu.utn.mdp.udee.converter;

import org.springframework.core.convert.TypeDescriptor;

public interface Convertible {

    Object convert(TypeDescriptor targetType);

}
