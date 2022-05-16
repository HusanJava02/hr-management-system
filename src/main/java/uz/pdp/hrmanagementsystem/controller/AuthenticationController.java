package uz.pdp.hrmanagementsystem.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagementsystem.dto.GenericResponse;
import uz.pdp.hrmanagementsystem.dto.JwtModel;
import uz.pdp.hrmanagementsystem.dto.UserLoginModel;
import uz.pdp.hrmanagementsystem.dto.UserModel;
import uz.pdp.hrmanagementsystem.service.JwtUtil;
import uz.pdp.hrmanagementsystem.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
@AllArgsConstructor
public class AuthenticationController {


    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    //FIRST Registration of User
    @PostMapping(value = "/create/user")
    public GenericResponse createUser(@Valid @RequestBody UserModel userModel){
        return userService.create(userModel);
    }

    @GetMapping(value = "/activate/account")
    public GenericResponse activateAccount(@RequestParam(name = "uniqueId") String verificationCode, @RequestParam(value = "email") String email) {
        return userService.activateUser(verificationCode,email);
    }

    @PostMapping(value = "/login")
    public JwtModel login(@Valid @RequestBody UserLoginModel model) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(model.getEmail(),model.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
            Map<String,Object> map = new HashMap<>();
            String token = jwtUtil.generateToken(map, model);
            return new JwtModel(token);
        }catch (Exception exception) {
            throw new BadCredentialsException(String.format("email or password error: email: %s",model.getEmail()));
        }

    }



    
}
