package go.tetz.where_back.common.exception;

public class OAuthException extends ApiException {

    public OAuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OAuthException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public OAuthException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public OAuthException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, errorCode.getMessage(), cause);
    }
}
