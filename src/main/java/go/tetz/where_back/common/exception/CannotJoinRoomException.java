package go.tetz.where_back.common.exception;

public class CannotJoinRoomException extends ApiException {

    public CannotJoinRoomException() {
        super(ErrorCode.CANNOT_JOIN_ROOM);
    }
}
