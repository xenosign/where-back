package go.tetz.where_back.location.service;

import go.tetz.where_back.location.entity.LocationMarker;
import go.tetz.where_back.location.repository.LocationMarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationMarkerRepository markerRepository;

    public List<LocationMarker> getSurroundingMarkers(Double lat, Double lng, Double radius) {
        return markerRepository.findMarkersWithinRadius(lat, lng, radius);
    }

    public boolean isUserAtMarker(Double userLat, Double userLng, Long markerId, Double thresholdMeter) {
        LocationMarker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마커입니다."));

        double distance = calculateDistance(userLat, userLng, marker.getLatitude(), marker.getLongitude());

        return distance <= (thresholdMeter / 1000.0); // m를 km로 변환하여 비교
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist * 60 * 1.1515 * 1.609344;
    }

    @Transactional
    public LocationMarker saveMarker(LocationMarker marker) {
        return markerRepository.save(marker);
    }

    public Optional<LocationMarker> getMarkerById(Long id) {
        return markerRepository.findById(id);
    }
}