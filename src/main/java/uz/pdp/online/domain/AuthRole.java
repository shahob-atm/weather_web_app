package uz.pdp.online.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthRole {
    private long id;
    private String name;
    private String code;
    private List<AuthPermission> permissions;
}
