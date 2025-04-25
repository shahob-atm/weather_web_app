package uz.pdp.online.service;

import org.springframework.stereotype.Service;
import uz.pdp.online.dao.CityDao;
import uz.pdp.online.domain.City;

import java.util.List;

@Service
public class CityService {
    private final CityDao cityDao;

    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public void subscribeToCity(long userId, int cityId) {
        cityDao.subscribeToCity(userId, cityId);
    }

    public void unsubscribeFromCity(long userId, int cityId) {
        cityDao.unsubscribeFromCity(userId, cityId);
    }

    public City findById(int cityId) {
        return cityDao.findById(cityId);
    }

    public List<City> getSubscriptions(long userId) {
        return cityDao.getSubscriptions(userId);
    }

    public List<City> getCities() {
        return cityDao.getCities();
    }

    public void update(City city) {
        cityDao.update(city);
    }

    public void create(City city) {
        cityDao.save(city);
    }
}
