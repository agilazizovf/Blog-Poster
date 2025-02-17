package az.project.blogposter.controller;

import az.project.blogposter.model.dto.request.ClientRequestDTO;
import az.project.blogposter.model.dto.request.LoginRequestDTO;
import az.project.blogposter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginReq)  {
        return userService.login(loginReq);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid ClientRequestDTO clientReq)  {
        userService.register(clientReq);
    }
}
