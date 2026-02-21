package go.tetz.where_back.location.dto;

import go.tetz.where_back.location.entity.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegionResponse {

    private Long id;
    private String name;
    private String country;
    private String description;

    @Builder
    public RegionResponse(Long id, String name, String country, String description) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
    }

    public static RegionResponse from(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .name(region.getName())
                .country(region.getCountry())
                .description(region.getDescription())
                .build();
    }
}
