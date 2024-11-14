package User.config;

import User.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        // System.out.println(token);
        if (token != null && token.startsWith("Bearer")) {
            String tokalval = token.substring(8, token.length() - 1);
            //  System.out.println(tokalval);
            String username = jwtService.getUsername(tokalval);
            System.out.println(username);
        }

        filterChain.doFilter(request, response);
    }
}
