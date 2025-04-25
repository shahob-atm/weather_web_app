package uz.pdp.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.online.config.security.SessionUser;
import uz.pdp.online.dao.CityDao;
import uz.pdp.online.dto.CityProjectionDto;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final CityDao cityDao;
    private final SessionUser sessionUser;

    public HomeController(CityDao cityDao, SessionUser sessionUser) {
        this.cityDao = cityDao;
        this.sessionUser = sessionUser;
    }

    @GetMapping
    public String homePage(Model model) {
        long id = sessionUser.getUser().getId();
        List<CityProjectionDto> cities = cityDao.getAllCitiesWithSubscriptionStatus(id);
        model.addAttribute("cities", cities);
        return "home";
    }
}
