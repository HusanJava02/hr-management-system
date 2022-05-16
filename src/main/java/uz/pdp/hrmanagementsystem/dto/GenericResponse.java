package uz.pdp.hrmanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericResponse {
    private boolean success;
    private String message;
    private Object object;
}
