package uz.pdp.online.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.online.domain.AuthRole;

import java.util.List;

@Component
public class AuthRoleDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthRoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AuthRole> getAuthRolesByUserId(long userId) {
        final String sql = """
        SELECT ar.id, ar.name, ar.code
        FROM auth_role ar
        JOIN auth_user_roles aur ON ar.id = aur.role_id
        WHERE aur.user_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            AuthRole role = new AuthRole();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            role.setCode(rs.getString("code"));
            return role;
        });
    }
}
