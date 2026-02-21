package go.tetz.where_back.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMeResponse {

    private Long userId;
    private String email;

    @Builder
    public UserMeResponse(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
