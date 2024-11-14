package User.controller;


import User.entity.User;
import User.payload.JWTToken;
import User.payload.LoginDto;
import User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> CreateUser(@RequestBody User user) {
        User user1 = userService.CreateUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{login}")
    public ResponseEntity<?> SignIn(@RequestBody LoginDto loginDto) {
        String token = userService.VerifyUser(loginDto);//boolean staus
        JWTToken jwtToken = new JWTToken();
        if (token != null) {
            jwtToken.setTokenType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findBYId(@PathVariable long id) {
        User byId = userService.findBYId(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);


    }

}



