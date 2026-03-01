package go.tetz.where_back.user.controller;

import go.tetz.where_back.user.dto.UserMeResponse;
import go.tetz.where_back.user.util.AuthenticationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자", description = "사용자 정보 API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationUtil authenticationUtil;

    @Operation(summary = "내 정보 조회", description = "JWT로 인증된 현재 사용자 정보 반환")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 필요")
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