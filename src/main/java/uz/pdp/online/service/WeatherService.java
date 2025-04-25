package uz.pdp.online.service;

import org.springframework.stereotype.Service;
import uz.pdp.online.dao.WeatherDao;
import uz.pdp.online.dto.WeatherProjectionDto;

import java.util.List;

@Service
public class WeatherService {
    private final WeatherDao weatherDao;

    public WeatherService(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    public List<WeatherProjectionDto> getWeathers(int cityId, int days) {
        return weatherDao.getWeathers(cityId, days);
    }
}
