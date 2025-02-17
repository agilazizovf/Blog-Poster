package az.project.blogposter.service.impl;

import az.project.blogposter.entity.Authority;
import az.project.blogposter.entity.Client;
import az.project.blogposter.entity.User;
import az.project.blogposter.model.ExceptionDTO;
import az.project.blogposter.model.dto.request.ClientRequestDTO;
import az.project.blogposter.model.dto.request.LoginRequestDTO;
import az.project.blogposter.model.dto.response.ClientResponseDTO;
import az.project.blogposter.model.dto.response.LoginResponseDTO;
import az.project.blogposter.model.enums.Status;
import az.project.blogposter.model.exception.AlreadyExistsException;
import az.project.blogposter.model.exception.UserNotFoundException;
import az.project.blogposter.repository.ClientRepository;
import az.project.blogposter.repository.UserRepository;
import az.project.blogposter.service.UserService;
import az.project.blogposter.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    @Override
    public ResponseEntity<?> login(LoginRequestDTO loginReq) {
        log.info("Authenticate method started by: {}", loginReq.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
            log.info("Authentication details: {}", authentication);
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with username: "+username));

            String token = jwtUtil.createToken(user);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            LoginResponseDTO loginRes = new LoginResponseDTO(username, token);

            log.info("User: {} logged in successfully", user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRes);

        } catch (BadCredentialsException e) {
            log.error("Authentication error: {}", e.getMessage());
            ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
        } catch (Exception e) {
            log.error("Unexpected error during authentication: {}", e.getMessage());
            ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
        }
    }

    @Override
    public ResponseEntity<ClientResponseDTO> register(ClientRequestDTO request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AlreadyExistsException("Username already exists");
        }

        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setUsername(request.getUsername());
        client.setBirthday(request.getBirthday());
        client.setEmail(request.getEmail());
        client.setCreatedTime(LocalDate.now());
        client.setUpdatedTime(LocalDate.now());

        clientRepository.save(client);

        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        Authority authority = new Authority("USER");
        Set<Authority> authoritySet = Set.of(authority);
        user.setAuthorities(authoritySet);
        user.setStatus(Status.ONLINE);
        userRepository.save(user);

        log.info("Saved new client and user with username: {}", client.getUsername());

        // Prepare response
        ClientResponseDTO response = new ClientResponseDTO();
        response.setUsername(request.getUsername());
        response.setStatus(Status.ONLINE);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
