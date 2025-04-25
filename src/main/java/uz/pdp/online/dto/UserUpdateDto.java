package uz.pdp.online.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private long id;
    private String username;
}
