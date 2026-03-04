package go.tetz.where_back.common.exception;

public class AlreadyJoinedException extends ApiException {

    public AlreadyJoinedException() {
        super(ErrorCode.ALREADY_JOINED);
    }
}
