package uz.pdp.online.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.online.dto.UserRegisterDto;
import uz.pdp.online.service.AuthUserService;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthUserService authUserService;

    public AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("dto", new UserRegisterDto());
        return "auth/register";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("dto") UserRegisterDto userRegisterDto,
                           BindingResult result, Model model,
                           @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println(result.toString());
        if (result.hasErrors()) {
            model.addAttribute("dto", userRegisterDto);
            return "auth/register";
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            String errorMessage = "Passwords do not match";
            result.rejectValue("confirmPassword", "", errorMessage);
            model.addAttribute("dto", userRegisterDto);
            return "auth/register";
        }

        authUserService.registerUser(userRegisterDto, file);

        return "redirect:/auth/login";
    }
}
