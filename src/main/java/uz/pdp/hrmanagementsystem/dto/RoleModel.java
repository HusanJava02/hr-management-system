package uz.pdp.hrmanagementsystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.hrmanagementsystem.entities.enums.RoleName;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class RoleModel {
    @NotNull(message = "requested null value")
    private RoleName roleName;

}
