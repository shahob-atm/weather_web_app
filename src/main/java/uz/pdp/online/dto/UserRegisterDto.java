package uz.pdp.online.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "username not be empty")
    private String username;
    @NotBlank(message = "password not be empty")
    private String password;
    @NotBlank(message = "confirm-password not be empty")
    private String confirmPassword;
}
