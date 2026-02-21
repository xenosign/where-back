package go.tetz.where_back.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateRegionRequest {

    @NotBlank(message = "지역 이름을 입력해주세요.")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "국가를 입력해주세요.")
    @Size(max = 100)
    private String country;

    @Size(max = 500)
    private String description;
}
