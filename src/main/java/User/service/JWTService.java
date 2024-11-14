package User.service;

import User.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmkey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.duration.expiry}")
    private int expiryTime;
    private Algorithm algorithm;
    private static final String USER_NAME = "username";

    @PostConstruct
    public void PostConstruct() throws UnsupportedEncodingException {
//        System.out.println(algorithmkey);
//        System.out.println(issuer);
//        System.out.println(expiryTime);
        algorithm = Algorithm.HMAC256(algorithmkey);


    }

    public String generateToken(User user) {
        return JWT.create()
                .withClaim(USER_NAME, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token) {
        DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
      return   verify.getClaim(USER_NAME).asString();
    }

}
