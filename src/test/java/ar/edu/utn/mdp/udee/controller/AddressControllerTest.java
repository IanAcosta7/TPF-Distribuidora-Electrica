package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
import ar.edu.utn.mdp.udee.service.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class AddressControllerTest {

    private AddressService addressServiceMock;
    private AddressController addressController;

    @BeforeAll
    public void setUp() {
        addressServiceMock = mock(AddressService.class);

        addressController = new AddressController(addressServiceMock);
    }

    @Test
    public void addTest() {
        // Arrange
        AddressDTO addressDTO = getAddressDTO();
        Mockito.when(addressServiceMock.add(addressDTO)).thenReturn(addressDTO);

        // Act
        ResponseEntity<AddressDTO> result = addressController.add(addressDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(addressDTO, result.getBody());
    }

    @Test
    public void deleteTest() {
        // Arrange
        Integer id = 1;
        Mockito.when(addressServiceMock.delete(id)).thenReturn(id);

        // Act
        ResponseEntity<Integer> result = addressController.delete(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getBody());
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

}
