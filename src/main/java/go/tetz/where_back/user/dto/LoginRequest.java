package go.tetz.where_back.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String accessToken;
    private String provider; // "kakao", "google" 등
}
