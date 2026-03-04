package go.tetz.where_back.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 404 Not Found
    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    GAME_ROOM_NOT_FOUND("GAME_ROOM_NOT_FOUND", "존재하지 않는 게임방입니다.", HttpStatus.NOT_FOUND),
    PARTICIPANT_NOT_FOUND("PARTICIPANT_NOT_FOUND", "참가자가 아닙니다.", HttpStatus.NOT_FOUND),
    LOCATION_MARKER_NOT_FOUND("LOCATION_MARKER_NOT_FOUND", "존재하지 않는 마커입니다.", HttpStatus.NOT_FOUND),
    REGION_NOT_FOUND("REGION_NOT_FOUND", "존재하지 않는 지역입니다.", HttpStatus.NOT_FOUND),

    // 400 Bad Request
    CANNOT_JOIN_ROOM("CANNOT_JOIN_ROOM", "참가할 수 없는 게임방입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_JOINED("ALREADY_JOINED", "이미 참가 중인 방입니다.", HttpStatus.BAD_REQUEST),
    GAME_NOT_STARTABLE("GAME_NOT_STARTABLE", "대기 중인 방만 게임을 시작할 수 있습니다.", HttpStatus.BAD_REQUEST),
    OAUTH_ERROR("OAUTH_ERROR", "OAuth 로그인 처리 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
    OAUTH_TOKEN_ERROR("OAUTH_TOKEN_ERROR", "액세스 토큰 발급에 실패했습니다.", HttpStatus.BAD_REQUEST),
    OAUTH_USER_INFO_ERROR("OAUTH_USER_INFO_ERROR", "사용자 정보 조회에 실패했습니다.", HttpStatus.BAD_REQUEST),

    // 403 Forbidden
    HOST_ONLY("HOST_ONLY", "방장만 게임을 시작할 수 있습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
