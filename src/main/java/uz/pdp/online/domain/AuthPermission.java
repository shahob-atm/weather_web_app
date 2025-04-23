package uz.pdp.online.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthPermission {
    private long id;
    private String name;
    private String code;
}
