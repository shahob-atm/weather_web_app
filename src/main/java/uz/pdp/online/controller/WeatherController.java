package uz.pdp.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.online.domain.City;
import uz.pdp.online.dto.WeatherProjectionDto;
import uz.pdp.online.service.CityService;
import uz.pdp.online.service.WeatherService;

import java.util.List;

@Controller
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final CityService cityService;

    public WeatherController(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    public String getWeathers(@PathVariable("id") int cityId, @RequestParam(name = "days", defaultValue = "1") int days, Model model) {
        List<WeatherProjectionDto> weatherList = weatherService.getWeathers(cityId, days);
        City city = cityService.findById(cityId);
        model.addAttribute("days", days);
        model.addAttribute("weathers", weatherList);
        model.addAttribute("cityId", cityId);
        model.addAttribute("city", city);
        return "city";
    }
}
