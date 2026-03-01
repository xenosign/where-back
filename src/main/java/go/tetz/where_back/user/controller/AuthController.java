package go.tetz.where_back.user.controller;

import go.tetz.where_back.user.dto.AuthResponse;
import go.tetz.where_back.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Tag(name = "인증", description = "카카오 OAuth 로그인 및 JWT 인증 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Value("${kakao.cookie-secure:false}")
    private boolean kakaoCookieSecure;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${kakao.front.redirect.url}")
    private String kakaoFrontRedirectUrl;

    @Operation(summary = "카카오 로그인", description = "카카오 OAuth 페이지로 리다이렉트")
    @GetMapping("/kakao")
    public RedirectView kakaoLogin() {
        String kakaoLoginUrl = authService.getKakaoLoginUrl();
        return new RedirectView(kakaoLoginUrl);
    }

    @Operation(summary = "카카오 로그인 (백엔드)", description = "state=backend로 카카오 로그인 후 JSON 응답 반환")
    @GetMapping("/kakao/backend")
    public RedirectView kakaoLoginForBackend() {
        String kakaoLoginUrl = authService.getKakaoLoginUrl("backend");
        return new RedirectView(kakaoLoginUrl);
    }

    @Operation(summary = "카카오 콜백", description = "카카오 OAuth 콜백 처리 (state=backend 시 JSON, 아니면 프론트 리다이렉트)")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "400", description = "로그인 실패")
    @GetMapping("/kakao/callback")
    public Object kakaoCallback(
            @Parameter(description = "카카오 인증 코드") @RequestParam String code,
            @Parameter(description = "state (backend: JSON 응답)") @RequestParam(required = false) String state,
            HttpServletResponse response) {

        try {
            AuthResponse authResponse = authService.processKakaoCallback(code);

            Cookie jwtCookie = new Cookie("jwt", authResponse.getAccessToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(kakaoCookieSecure);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge((int) (jwtExpiration / 1000));
            jwtCookie.setAttribute("SameSite", "None");
            response.addCookie(jwtCookie);

            if ("backend".equals(state)) {
                return ResponseEntity.ok(authResponse);
            } else {
                String redirectUrl = kakaoFrontRedirectUrl +
                        "?token=" + authResponse.getAccessToken();
                return new RedirectView(redirectUrl);
            }

        } catch (Exception e) {
            log.error("카카오 OAuth 콜백 처리 오류", e);

            if ("backend".equals(state)) {
                return ResponseEntity.badRequest().build();
            } else {
                return new RedirectView(kakaoFrontRedirectUrl + "?error=auth_failed");
            }
        }
    }


    @Operation(summary = "카카오 로그인 URL 조회", description = "카카오 OAuth 로그인 페이지 URL 반환")
    @GetMapping("/kakao/url")
    public ResponseEntity<Map<String, String>> getKakaoLoginUrl() {
        String kakaoLoginUrl = authService.getKakaoLoginUrl();
        return ResponseEntity.ok(Map.of("loginUrl", kakaoLoginUrl));
    }

    @Operation(summary = "카카오 액세스 토큰으로 로그인", description = "카카오에서 발급받은 액세스 토큰으로 JWT 발급")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "400", description = "로그인 실패")
    @PostMapping("/kakao")
    public ResponseEntity<AuthResponse> kakaoLogin(
            @Parameter(description = "카카오 액세스 토큰") @RequestParam String accessToken,
                                                   HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.processKakaoLogin(accessToken);

            Cookie jwtCookie = new Cookie("jwt", authResponse.getAccessToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(kakaoCookieSecure);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge((int) (jwtExpiration / 1000));
            jwtCookie.setAttribute("SameSite", "None");

            response.addCookie(jwtCookie);

            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.error("카카오 로그인 오류", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "로그아웃", description = "JWT 쿠키 삭제")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(kakaoCookieSecure);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        jwtCookie.setAttribute("SameSite", "None");

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }
}