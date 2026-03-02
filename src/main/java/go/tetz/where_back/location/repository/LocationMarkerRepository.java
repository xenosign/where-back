package go.tetz.where_back.location.repository;

import go.tetz.where_back.location.entity.LocationMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationMarkerRepository extends JpaRepository<LocationMarker, Long> {
    List<LocationMarker> findByType(String type);

    List<LocationMarker> findByRegionId(Long regionId);

    List<LocationMarker> findByRegion_Name(String regionName);

    @Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(latitude)))) AS distance " +
            "FROM location_markers " +
            "HAVING distance <= :radius " +
            "ORDER BY distance",
            nativeQuery = true)
    List<LocationMarker> findMarkersWithinRadius(
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("radius") Double radius
    );
}