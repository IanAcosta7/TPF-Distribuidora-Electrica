package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
import ar.edu.utn.mdp.udee.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ConversionService conversionService;

    @Autowired
    public AddressService(AddressRepository addressRepository, ConversionService conversionService) {
        this.addressRepository = addressRepository;
        this.conversionService = conversionService;
    }

    public AddressDTO getAddressByUserId(Integer userId) {
        return conversionService.convert(addressRepository.findByClient(userId), AddressDTO.class);
    }

}
