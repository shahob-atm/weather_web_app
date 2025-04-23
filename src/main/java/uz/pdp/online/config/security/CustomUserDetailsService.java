package uz.pdp.online.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.online.domain.AuthPermission;
import uz.pdp.online.domain.AuthRole;
import uz.pdp.online.domain.AuthUser;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthUserDao authUserDao;
    private final AuthRoleDao authRoleDao;
    private final AuthPermissionDao authPermissionDao;

    @Autowired
    public CustomUserDetailsService(AuthUserDao authUserDao, AuthRoleDao authRoleDao, AuthPermissionDao authPermissionDao) {
        this.authUserDao = authUserDao;
        this.authRoleDao = authRoleDao;
        this.authPermissionDao = authPermissionDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findAuthUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("This username -> " + username + " not found"));

        List<AuthRole> authRoleList = authRoleDao.getAuthRolesByUserId(authUser.getId());

        authRoleList.forEach(authRole -> {
            List<AuthPermission> permissions = authPermissionDao.getAllByRoleId(authRole.getId());
            authRole.setPermissions(permissions);
        });

        authUser.setRoles(authRoleList);

        return new CustomUserDetails(authUser);
    }
}
