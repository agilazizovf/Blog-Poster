package az.project.blogposter.service;

import az.project.blogposter.model.dto.request.ClientRequestDTO;
import az.project.blogposter.model.dto.request.LoginRequestDTO;
import az.project.blogposter.model.dto.response.ClientResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> login(LoginRequestDTO loginReq);
    ResponseEntity<ClientResponseDTO> register(ClientRequestDTO clientAddRequest);
}
