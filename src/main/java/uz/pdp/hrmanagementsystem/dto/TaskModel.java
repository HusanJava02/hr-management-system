package uz.pdp.hrmanagementsystem.dto;

import lombok.*;
import uz.pdp.hrmanagementsystem.entities.enums.Condition;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskModel {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Condition condition;

    @NotNull
    private UserModel user;

}
