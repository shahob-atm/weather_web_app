package uz.pdp.online.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import uz.pdp.online.domain.City;
import uz.pdp.online.dto.CityProjectionDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CityDao {
    private final JdbcTemplate jdbcTemplate;

    public CityDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CityProjectionDto> getAllCitiesWithSubscriptionStatus(long userId) {
        String sql = """
        SELECT c.id AS city_id,
               c.name AS city_name,
               CASE\s
                   WHEN ucs.user_id IS NOT NULL THEN TRUE
                   ELSE FALSE
               END AS subscribed
        FROM city c
        LEFT JOIN user_city_subscription ucs\s
            ON c.id = ucs.city_id AND ucs.user_id = ?
   \s""";

        return jdbcTemplate.query(
                sql,
                new Object[]{userId},
                (rs, rowNum) -> new CityProjectionDto(
                        rs.getInt("city_id"),
                        rs.getString("city_name"),
                        rs.getBoolean("subscribed")
                )
        );
    }

    public void subscribeToCity(Long userId, int cityId) {
        String checkSql = "SELECT COUNT(*) FROM user_city_subscription WHERE user_id = ? AND city_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, userId, cityId);

        if (count == 0) {
            String sql = "INSERT INTO user_city_subscription (user_id, city_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, userId, cityId);
        }
    }

    public void unsubscribeFromCity(Long userId, int cityId) {
        String sql = "DELETE FROM user_city_subscription WHERE user_id = ? AND city_id = ?";
        jdbcTemplate.update(sql, userId, cityId);
    }

    public City findById(int cityId) {
        String sql = "SELECT id, name FROM city WHERE id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{cityId},
                new RowMapper<City>() {
                    @Override
                    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                        City city = new City();
                        city.setId(rs.getInt("id"));
                        city.setName(rs.getString("name"));
                        return city;
                    }
                });
    }

    public List<City> getSubscriptions(long userId) {
        final String sql = """
        SELECT c.id, c.name
        FROM city c
        JOIN user_city_subscription ucs ON c.id = ucs.city_id
        WHERE ucs.user_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            City city = new City();
            city.setId(rs.getInt("id"));
            city.setName(rs.getString("name"));
            return city;
        });
    }

    public List<City> getCities() {
        final String sql = """
        SELECT c.id, c.name
        FROM city c
    """;

        return jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) -> {
            City city = new City();
            city.setId(rs.getInt("id"));
            city.setName(rs.getString("name"));
            return city;
        });
    }

    public void update(City city) {
        String sql = "UPDATE city SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, city.getName(), city.getId());
    }

    public void save(City city) {
        String sql = "INSERT INTO city (name) VALUES (?)";
        jdbcTemplate.update(sql, city.getName());
    }
}
