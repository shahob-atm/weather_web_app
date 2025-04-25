package uz.pdp.online.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Weather {
    private long id;
    private LocalDateTime dateTime;
    private double temperature;
    private String description;
    private int cityId;
}
