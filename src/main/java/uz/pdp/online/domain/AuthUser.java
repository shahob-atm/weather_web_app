package uz.pdp.online.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthUser {
    private long id;
    private String username;
    private String password;
    private List<AuthRole> roles;
    private boolean enabled;
    private String photoUrl;
}
