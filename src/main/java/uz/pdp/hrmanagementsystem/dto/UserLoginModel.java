package uz.pdp.hrmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserLoginModel {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;
}
