package go.tetz.where_back.common.exception;

public class GameNotStartableException extends ApiException {

    public GameNotStartableException() {
        super(ErrorCode.GAME_NOT_STARTABLE);
    }
}
