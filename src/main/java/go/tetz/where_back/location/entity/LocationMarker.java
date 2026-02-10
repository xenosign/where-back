package go.tetz.where_back.location.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "location_markers")
public class LocationMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 장소 이름 (예: 강남역 11번 출구)

    @Column(nullable = false)
    private Double latitude; // 위도

    @Column(nullable = false)
    private Double longitude; // 경도

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String type; // 마커 종류 (BASE, ITEM_SHOP, SAFE_ZONE 등)

    private String description; // 장소 설명

    @Builder
    public LocationMarker(String name, Double latitude, Double longitude, String type, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.description = description;
    }
}
