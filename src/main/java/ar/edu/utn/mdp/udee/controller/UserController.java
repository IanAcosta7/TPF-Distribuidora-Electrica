package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.user.UserLoginDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserTypeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.dto.user.UserDTO;
import ar.edu.utn.mdp.udee.service.UserService;
import ar.edu.utn.mdp.udee.service.UserTypeService;
import ar.edu.utn.mdp.udee.util.EntityURLBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.utn.mdp.udee.util.Constants.JWT_SECRET;

@RestController
@RequestMapping(UserController.PATH)
public class UserController {

    final public static String PATH = "/users";
    final public static String TYPE_PATH = "/types";
    final public static String LOGIN_PATH = "/login";

    private final UserService userService;
    private final UserTypeService userTypeService;

    @Autowired
    public UserController(UserService userService, UserTypeService userTypeService) {
        this.userService = userService;
        this.userTypeService = userTypeService;
    }

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<UserLoginDTO> login(@RequestHeader("authorization") String credentials) {
        ResponseEntity<UserLoginDTO> response;
        String[] userData = credentials.split(" ")[1].split(":");

        String username = userData[0];
        String password = userData[1];

        UserDTO userDTO = userService.login(username, password);

        if (userDTO != null) {
            response = ResponseEntity.ok(new UserLoginDTO(generateToken(userDTO)));
        } else {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return response;
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

    @GetMapping(TYPE_PATH + "/{id}")
    public ResponseEntity<UserTypeDTO> getUserTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(userTypeService.getById(id));
    }

    private String generateToken(UserDTO userDTO) {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_CLIENT");

            return Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(userDTO.getUsername())
                    .claim("userid", userDTO.getId())
                    .claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 600000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();


    }
}
