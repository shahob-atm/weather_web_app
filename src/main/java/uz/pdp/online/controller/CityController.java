package uz.pdp.online.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.config.security.SessionUser;
import uz.pdp.online.domain.City;
import uz.pdp.online.service.CityService;

import java.util.List;

@Controller
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;
    private final SessionUser sessionUser;

    public CityController(CityService cityService, SessionUser sessionUser) {
        this.cityService = cityService;
        this.sessionUser = sessionUser;
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam("cityId") int cityId) {
        long userId = sessionUser.getUser().getId();
        cityService.subscribeToCity(userId, cityId);
        return "redirect:/home";
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestParam("cityId") int cityId) {
        long userId = sessionUser.getUser().getId();
        cityService.unsubscribeFromCity(userId, cityId);
        return "redirect:/home";
    }

    @GetMapping("/subscriptions/{id}")
    public String getSubscriptions(@PathVariable("id") long userId, Model model) {
        List<City> cities = cityService.getSubscriptions(userId);
        model.addAttribute("cities", cities);
        return "userSubscriptions";
    }

    @GetMapping("/cities")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getCities(Model model) {
        List<City> cities = cityService.getCities();
        model.addAttribute("cities", cities);
        return "cities";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        City city = cityService.findById(id);
        model.addAttribute("city", city);
        return "cityEdit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute City city) {
        cityService.update(city);
        return "redirect:/city/cities";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("city", new City());
        return "createCity";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute City city) {
        cityService.create(city);
        return "redirect:/city/cities";
    }
}
