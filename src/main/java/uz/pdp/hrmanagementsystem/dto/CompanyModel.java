package uz.pdp.hrmanagementsystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@ToString
public class CompanyModel {

    private Long id;

    @NotNull
    private String companyName;

    private String email;

    private String phoneNumber;
}
