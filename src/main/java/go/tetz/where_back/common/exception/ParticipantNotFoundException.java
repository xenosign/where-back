package go.tetz.where_back.common.exception;

public class ParticipantNotFoundException extends ApiException {

    public ParticipantNotFoundException() {
        super(ErrorCode.PARTICIPANT_NOT_FOUND);
    }

    public ParticipantNotFoundException(Long roomId, Long userId) {
        super(ErrorCode.PARTICIPANT_NOT_FOUND,
                "참가자가 아닙니다. (roomId=" + roomId + ", userId=" + userId + ")");
    }
}
