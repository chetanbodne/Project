package User.payload;

import lombok.Data;

@Data
public class JWTToken {

    private String TokenType;
    private String Token;
}
