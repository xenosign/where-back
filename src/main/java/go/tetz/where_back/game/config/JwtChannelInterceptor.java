package go.tetz.where_back.game.config;

import go.tetz.where_back.common.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

/**
 * STOMP CONNECT 시 JWT 검증 및 인증 정보 설정.
 * 클라이언트는 CONNECT 시 headers에 token을 담아 전송:
 * - Authorization: Bearer {jwt}
 * - 또는 X-Auth-Token: {jwt}
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor, Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 99;
    }

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            return message;
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = resolveToken(accessor);
            if (token != null && jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getUserIdFromToken(token);
                String email = jwtUtil.getEmailFromToken(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userId, null, new ArrayList<>());
                auth.setDetails(email);
                accessor.setUser(auth);
                log.debug("WebSocket 인증 성공: userId={}", userId);
            } else {
                log.warn("WebSocket CONNECT 실패: 유효하지 않은 토큰");
            }
        }

        // /app/game/** 전송 시 인증 필수
        if (StompCommand.SEND.equals(accessor.getCommand())) {
            String destination = accessor.getDestination();
            if (destination != null && destination.startsWith("/app/game/") && accessor.getUser() == null) {
                log.warn("인증되지 않은 WebSocket 메시지 차단: destination={}", destination);
                return null;
            }
        }

        return message;
    }

    private String resolveToken(StompHeaderAccessor accessor) {
        // Authorization: Bearer {token}
        List<String> authHeaders = accessor.getNativeHeader(AUTHORIZATION_HEADER);
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String value = authHeaders.get(0);
            if (value != null && value.startsWith(BEARER_PREFIX)) {
                return value.substring(BEARER_PREFIX.length()).trim();
            }
        }

        // X-Auth-Token: {token}
        List<String> tokenHeaders = accessor.getNativeHeader(AUTH_TOKEN_HEADER);
        if (tokenHeaders != null && !tokenHeaders.isEmpty()) {
            String value = tokenHeaders.get(0);
            return value != null ? value.trim() : null;
        }

        return null;
    }
}
