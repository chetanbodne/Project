package User.service;


import User.entity.User;
import User.payload.LoginDto;
import User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    public User CreateUser(User user) {
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashpw);
        User save = userRepository.save(user);
        return save;
    }

    public List<User> getAll() {
        List<User> all = userRepository.findAll();
        return all;
    }

    public String VerifyUser(LoginDto loginDto) {
        Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsername());
        if (byUsername.isPresent()) {

            User user = byUsername.get();
            // return BCrypt.checkpw(loginDto.getPassword(), user.getPassword());
            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) ;
            return jwtService.generateToken(user);
        }
        return null;

    }

    public User findBYId(long id) {
        Optional<User> byId = userRepository.findById(id);
        User user = byId.get();
        return user;
    }
}
