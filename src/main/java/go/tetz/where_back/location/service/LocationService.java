package go.tetz.where_back.location.service;

import go.tetz.where_back.location.dto.CreateMarkerRequest;
import go.tetz.where_back.location.dto.CreateRegionRequest;
import go.tetz.where_back.location.dto.MarkerResponse;
import go.tetz.where_back.location.dto.RegionResponse;
import go.tetz.where_back.location.entity.LocationMarker;
import go.tetz.where_back.location.entity.Region;
import go.tetz.where_back.location.repository.LocationMarkerRepository;
import go.tetz.where_back.location.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationMarkerRepository markerRepository;
    private final RegionRepository regionRepository;

    public List<LocationMarker> getSurroundingMarkers(Double lat, Double lng, Double radius) {
        return markerRepository.findMarkersWithinRadius(lat, lng, radius);
    }

    public List<MarkerResponse> getSurroundingMarkerResponses(Double lat, Double lng, Double radius) {
        return markerRepository.findMarkersWithinRadius(lat, lng, radius).stream()
                .map(MarkerResponse::from)
                .collect(Collectors.toList());
    }

    public boolean isUserAtMarker(Double userLat, Double userLng, Long markerId, Double thresholdMeter) {
        LocationMarker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마커입니다."));

        double distance = calculateDistance(userLat, userLng, marker.getLatitude(), marker.getLongitude());

        return distance <= (thresholdMeter / 1000.0);
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

    @Transactional
    public MarkerResponse createMarker(CreateMarkerRequest request) {
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다."));
        LocationMarker marker = LocationMarker.builder()
                .name(request.getName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .type(request.getType())
                .description(request.getDescription())
                .region(region)
                .build();
        marker = markerRepository.save(marker);
        return MarkerResponse.from(marker);
    }

    public Optional<LocationMarker> getMarkerById(Long id) {
        return markerRepository.findById(id);
    }

    public Optional<MarkerResponse> getMarkerResponseById(Long id) {
        return markerRepository.findById(id).map(MarkerResponse::from);
    }

    public List<MarkerResponse> getMarkersByRegionId(Long regionId) {
        return markerRepository.findByRegionId(regionId).stream()
                .map(MarkerResponse::from)
                .collect(Collectors.toList());
    }

    public List<MarkerResponse> getMarkersByRegionName(String regionName) {
        return markerRepository.findByRegionName(regionName).stream()
                .map(MarkerResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    @Transactional
    public RegionResponse createRegion(CreateRegionRequest request) {
        Region region = Region.builder()
                .name(request.getName())
                .country(request.getCountry())
                .description(request.getDescription())
                .build();
        region = regionRepository.save(region);
        return RegionResponse.from(region);
    }

    public Optional<Region> getRegionById(Long id) {
        return regionRepository.findById(id);
    }

    public Optional<RegionResponse> getRegionResponseById(Long id) {
        return regionRepository.findById(id).map(RegionResponse::from);
    }

    public Optional<Region> getRegionByName(String name) {
        return regionRepository.findByName(name);
    }

    public List<RegionResponse> getAllRegions() {
        return regionRepository.findAll().stream()
                .map(RegionResponse::from)
                .collect(Collectors.toList());
    }
}