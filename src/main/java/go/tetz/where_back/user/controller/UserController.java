package go.tetz.where_back.user.controller;

import go.tetz.where_back.user.dto.UserMeResponse;
import go.tetz.where_back.user.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationUtil authenticationUtil;

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> getCurrentUser() {
        Long userId = authenticationUtil.getCurrentUserId();
        String email = authenticationUtil.getCurrentUserEmail();

        UserMeResponse response = UserMeResponse.builder()
                .userId(userId)
                .email(email)
                .build();
        return ResponseEntity.ok(response);
    }
}