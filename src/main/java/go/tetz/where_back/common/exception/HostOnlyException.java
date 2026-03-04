package go.tetz.where_back.common.exception;

public class HostOnlyException extends ApiException {

    public HostOnlyException() {
        super(ErrorCode.HOST_ONLY);
    }
}
