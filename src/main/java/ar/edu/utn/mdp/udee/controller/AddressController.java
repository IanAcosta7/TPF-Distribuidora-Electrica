package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
import ar.edu.utn.mdp.udee.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AddressController.PATH)
public class AddressController {

    public static final String PATH = "/addresses";

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressDTO> add(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.add(addressDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(addressService.delete(id));
    }

}
