package go.tetz.where_back.common.exception;

public class LocationMarkerNotFoundException extends ApiException {

    public LocationMarkerNotFoundException() {
        super(ErrorCode.LOCATION_MARKER_NOT_FOUND);
    }

    public LocationMarkerNotFoundException(Long markerId) {
        super(ErrorCode.LOCATION_MARKER_NOT_FOUND, "존재하지 않는 마커입니다. (markerId=" + markerId + ")");
    }
}
