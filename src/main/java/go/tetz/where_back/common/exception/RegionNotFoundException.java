package go.tetz.where_back.common.exception;

public class RegionNotFoundException extends ApiException {

    public RegionNotFoundException() {
        super(ErrorCode.REGION_NOT_FOUND);
    }

    public RegionNotFoundException(Long regionId) {
        super(ErrorCode.REGION_NOT_FOUND, "존재하지 않는 지역입니다. (regionId=" + regionId + ")");
    }
}
