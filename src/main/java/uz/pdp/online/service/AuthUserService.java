package uz.pdp.online.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.online.dao.AuthUserDao;
import uz.pdp.online.domain.AuthUser;
import uz.pdp.online.dto.UserProjectionDto;
import uz.pdp.online.dto.UserRegisterDto;
import uz.pdp.online.dto.UserUpdateDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthUserService {
    private static final Path rootPath = Path.of(System.getProperty("user.dir"), "files");

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;

    public AuthUserService(AuthUserDao authUserDao, PasswordEncoder passwordEncoder) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDto dto, MultipartFile file) throws IOException {
        String filename = null;

        if (file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
            filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path savePath = rootPath.resolve(filename);
            Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
        }

        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .photoUrl(filename)
                .enabled(true)
                .build();

        authUserDao.saveAuthUser(authUser);
    }

    public List<UserProjectionDto> getUsers() {
        return authUserDao.getUsers();
    }

    public Optional<UserUpdateDto> getUserById(long id) {
        return authUserDao.getUserById(id);
    }

    public void update(UserUpdateDto userUpdateDto) {
        authUserDao.update(userUpdateDto);
    }
}
