package uz.pdp.online.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CityProjectionDto {
    private int cityId;
    private String cityName;
    private boolean subscribed;
}
