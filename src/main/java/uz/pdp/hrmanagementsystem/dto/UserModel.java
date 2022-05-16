package uz.pdp.hrmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModel {

    private UUID id;

    @Email(message = "requested invalid email")
    private String email;

    @NotNull(message = "password requested null value")
    @Size(min = 8)
    private String password;

    @NotNull(message = "firstname requested null value")
    private String firstname;

    @NotNull(message = "lastname, requested null value")
    private String lastname;

//    @NotNull(message = "phone number requested null value")
    private String phoneNumber;

    private CompanyModel company;

    @Valid
    private Set<RoleModel> roles;
}
