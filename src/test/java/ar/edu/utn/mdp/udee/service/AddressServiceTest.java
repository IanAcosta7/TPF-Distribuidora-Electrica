package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Address;
import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
import ar.edu.utn.mdp.udee.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class AddressServiceTest {

    private AddressRepository addressRepositoryMock;
    private ConversionService conversionServiceMock;
    private AddressService addressService;

    @BeforeAll
    public void setUp() {
        addressRepositoryMock = mock(AddressRepository.class);
        conversionServiceMock = mock(ConversionService.class);

        addressService = new AddressService(addressRepositoryMock, conversionServiceMock);
    }

    @Test
    public void addTest() {
        // Arrange
        AddressDTO addressDTO = getAddressDTO();
        Address address = getAddress();

        Mockito.when(conversionServiceMock.convert(addressDTO, Address.class)).thenReturn(address);
        Mockito.when(addressRepositoryMock.save(address)).thenReturn(address);
        Mockito.when(conversionServiceMock.convert(address, AddressDTO.class)).thenReturn(addressDTO);

        // Act
        AddressDTO result = addressService.add(addressDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(addressDTO, result);
    }

    @Test
    public void deleteTest() {
        // Arrange
        Integer id = 1;

        // Act
        Integer result = addressService.delete(id);

        // Assert
        Assertions.assertEquals(id, result);
    }

    private AddressDTO getAddressDTO() {
        return new AddressDTO(
                1,
                "Test",
                "Test",
                null,
                null,
                null
        );
    }

    private Address getAddress() {
        return new Address(
                1,
                "Test",
                "Test",
                null,
                null,
                null
        );
    }

}
