package uz.pdp.online.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.online.domain.AuthPermission;

import java.util.List;

@Component
public class AuthPermissionDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthPermissionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AuthPermission> getAllByRoleId(long roleId) {
        final String sql = """
        SELECT p.id, p.name, p.code
        FROM auth_permission p
        JOIN auth_role_permissions rp ON p.id = rp.permission_id
        WHERE rp.role_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{roleId}, (rs, rowNum) -> {
            AuthPermission permission = new AuthPermission();
            permission.setId(rs.getLong("id"));
            permission.setName(rs.getString("name"));
            permission.setCode(rs.getString("code"));
            return permission;
        });
    }
}
