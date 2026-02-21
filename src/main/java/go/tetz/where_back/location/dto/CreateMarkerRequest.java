package go.tetz.where_back.location.dto;

import go.tetz.where_back.location.entity.MarkerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMarkerRequest {

    @NotBlank(message = "마커 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "위도를 입력해주세요.")
    private Double latitude;

    @NotNull(message = "경도를 입력해주세요.")
    private Double longitude;

    @NotNull(message = "마커 타입을 선택해주세요.")
    private MarkerType type;

    private String description;

    @NotNull(message = "지역 ID를 입력해주세요.")
    private Long regionId;
}
