package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Address;
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

    public AddressDTO add(AddressDTO addressDTO) {
        Address address = conversionService.convert(addressDTO, Address.class);
        return conversionService.convert(addressRepository.save(address), AddressDTO.class);
    }

    public Integer delete(Integer id) {
        addressRepository.deleteById(id);
        return id;
    }
}
