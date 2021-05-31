package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.DTO.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.DTO.UserDTO;
import ar.edu.utn.mdp.udee.service.UserService;
import ar.edu.utn.mdp.udee.service.UserTypeService;
import ar.edu.utn.mdp.udee.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(UserController.PATH)
public class UserController {

    final public static String PATH = "/User";
    final public static String TYPE_PATH = "/Type";

    private final UserService userService;
    private final UserTypeService userTypeService;

    @Autowired
    public UserController(UserService userService, UserTypeService userTypeService) {
        this.userService = userService;
        this.userTypeService = userTypeService;
    }

    @PostMapping("/login")
    public Integer login(@RequestHeader("authorization") String credentials) {
        String[] userData = credentials.split(" ")[1].split(":");
        String username = userData[0];
        String password = userData[1];
        return userService.login(username, password);
    }

    @PostMapping
    public ResponseEntity<UserDTO> add(@RequestBody UserDTO userDTO) {
        UserDTO userDTOAdded = userService.add(userDTO);

        return ResponseEntity.created(
                URI.create(
                        EntityURLBuilder.buildURL(
                                UserController.PATH,
                                userDTOAdded.getId()
                        )
                )
        ).body(userDTOAdded);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<UserDTO>> get(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "50") Integer size) {
        return ResponseEntity.ok(userService.get(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping(TYPE_PATH)
    public ResponseEntity<UserTypeDTO> addType(@RequestBody UserTypeDTO userTypeDTO) {
        UserTypeDTO userTypeDTOAdded = userTypeService.addType(userTypeDTO);
        return ResponseEntity.created(
                URI.create(
                        EntityURLBuilder.buildURL(
                                UserController.PATH + UserController.TYPE_PATH,
                                userTypeDTOAdded.getId()
                        )
                )
        ).body(userTypeDTOAdded);
    }

    @GetMapping(TYPE_PATH)
    public ResponseEntity<PaginationResponse<UserTypeDTO>> getTypes(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "50") Integer size) {
        return ResponseEntity.ok(userTypeService.getTypes(page, size));
    }

    @PutMapping("/{id}" + TYPE_PATH + "/{typeId}")
    public Integer addTypeToUser(@PathVariable Integer id, @PathVariable Integer typeId) {
        return userService.addTypeToUser(id, typeId);
    }
}
