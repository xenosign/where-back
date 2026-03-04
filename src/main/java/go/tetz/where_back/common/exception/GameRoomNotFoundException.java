package go.tetz.where_back.common.exception;

public class GameRoomNotFoundException extends ApiException {

    public GameRoomNotFoundException() {
        super(ErrorCode.GAME_ROOM_NOT_FOUND);
    }

    public GameRoomNotFoundException(Long roomId) {
        super(ErrorCode.GAME_ROOM_NOT_FOUND, "존재하지 않는 게임방입니다. (roomId=" + roomId + ")");
    }
}
