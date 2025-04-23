package uz.pdp.online.dao;

import lombok.NonNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.online.domain.AuthUser;

import java.util.Optional;

@Component
public class AuthUserDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<AuthUser> findAuthUserByUsername(@NonNull String username) {
        final String sql = "SELECT * FROM auth_user WHERE username = ?";

        try {
            AuthUser authUser = jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                AuthUser user = new AuthUser();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setPhotoUrl(rs.getString("photo_url"));
                return user;
            });
            System.out.println(authUser);

            return Optional.ofNullable(authUser);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
