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
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MarkerType type;

    private String description;

    @Builder
    public LocationMarker(String name, Double latitude, Double longitude, MarkerType type, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.description = description;
    }
}
