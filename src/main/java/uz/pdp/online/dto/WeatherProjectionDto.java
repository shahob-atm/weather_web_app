package uz.pdp.online.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeatherProjectionDto {
    private long id;
    private LocalDateTime dateTime;
    private Integer temperature;
    private String description;
}
