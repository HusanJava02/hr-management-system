package uz.pdp.hrmanagementsystem.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagementsystem.dto.GenericResponse;
import uz.pdp.hrmanagementsystem.dto.UserModel;
import uz.pdp.hrmanagementsystem.service.JwtUtil;
import uz.pdp.hrmanagementsystem.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping
    public UserModel create(@Valid @RequestBody UserModel userModel, @RequestHeader(name = "Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7));
        return userService.addUserToCompany(userModel,email);
    }

    @PutMapping
    public UserModel update(@Valid @RequestBody UserModel userModel) {
        return userService.update(userModel);
    }

    @GetMapping
    public List<UserModel> getUsers(@RequestParam(value = "page",defaultValue = "0") Integer page) {
        return userService.getUsers(page);
    }

    @DeleteMapping(value = "/{id}")
    public GenericResponse delete(@PathVariable UUID id) {
        return userService.delete(id);
    }

}
