package uz.pdp.online.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserProjectionDto {
    private long id;
    private String username;
    private String photoUrl;
}
