package uz.pdp.online.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import uz.pdp.online.dto.WeatherProjectionDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WeatherDao {
    private final JdbcTemplate jdbcTemplate;

    public WeatherDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WeatherProjectionDto> getWeathers(int cityId, int days) {
        String sql = "SELECT id, date_time, temperature, description FROM weather WHERE city_id = ? limit ?";

        return jdbcTemplate.query(sql, new Object[]{cityId, days}, new RowMapper<WeatherProjectionDto>() {
            @Override
            public WeatherProjectionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                long weatherId = rs.getLong("id");
                LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
                Integer temperature = (int) rs.getDouble("temperature");
                String description = rs.getString("description");

                return new WeatherProjectionDto(weatherId, dateTime, temperature, description);
            }
        });
    }
}
