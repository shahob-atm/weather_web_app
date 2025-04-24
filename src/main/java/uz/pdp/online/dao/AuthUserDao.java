package uz.pdp.online.dao;

import lombok.NonNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import uz.pdp.online.domain.AuthUser;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthUserDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long saveAuthUser(@NonNull AuthUser authUser) {
        String sql = "insert into auth_user (username, password, enabled, photo_url) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, authUser.getUsername());
            ps.setString(2, authUser.getPassword());
            ps.setBoolean(3, authUser.isEnabled());
            ps.setString(4, authUser.getPhotoUrl());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeyList().getFirst();
        return keys.get("id") != null ? ((Number) keys.get("id")).longValue() : null;
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
