package go.tetz.where_back.location.dto;

import go.tetz.where_back.location.entity.LocationMarker;
import go.tetz.where_back.location.entity.MarkerType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarkerResponse {

    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private MarkerType type;
    private String description;
    private Long regionId;
    private String regionName;

    @Builder
    public MarkerResponse(Long id, String name, Double latitude, Double longitude,
                          MarkerType type, String description, Long regionId, String regionName) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.description = description;
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public static MarkerResponse from(LocationMarker marker) {
        Long regionId = marker.getRegion() != null ? marker.getRegion().getId() : null;
        String regionName = marker.getRegion() != null ? marker.getRegion().getName() : null;
        return MarkerResponse.builder()
                .id(marker.getId())
                .name(marker.getName())
                .latitude(marker.getLatitude())
                .longitude(marker.getLongitude())
                .type(marker.getType())
                .description(marker.getDescription())
                .regionId(regionId)
                .regionName(regionName)
                .build();
    }
}
