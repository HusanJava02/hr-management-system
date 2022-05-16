package uz.pdp.hrmanagementsystem.configurations.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.hrmanagementsystem.dto.errors.ApiError;
import uz.pdp.hrmanagementsystem.entities.User;
import uz.pdp.hrmanagementsystem.exceptions.CommonJwtException;
import uz.pdp.hrmanagementsystem.service.JwtUtil;
import uz.pdp.hrmanagementsystem.service.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (!Objects.isNull(token) && token.startsWith("Bearer ")) {
            String userEmail = jwtUtil.extractEmail(token.substring(7));
            User user = (User) userDetailsService.loadUserByUsername(userEmail);
            Hibernate.initialize(user.getRoles());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getRoles()));
        }
        filterChain.doFilter(request, response);

    }


}
