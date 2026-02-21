package go.tetz.where_back.location.controller;

import go.tetz.where_back.location.dto.CreateMarkerRequest;
import go.tetz.where_back.location.dto.CreateRegionRequest;
import go.tetz.where_back.location.dto.MarkerResponse;
import go.tetz.where_back.location.dto.RegionResponse;
import go.tetz.where_back.location.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/markers")
    public ResponseEntity<MarkerResponse> createMarker(@Valid @RequestBody CreateMarkerRequest request) {
        MarkerResponse saved = locationService.createMarker(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/markers")
    public ResponseEntity<List<MarkerResponse>> getNearbyMarkers(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "0.5") Double radius) {
        List<MarkerResponse> markers = locationService.getSurroundingMarkerResponses(lat, lng, radius);
        return ResponseEntity.ok(markers);
    }

    @GetMapping("/markers/{id}")
    public ResponseEntity<MarkerResponse> getMarkerDetail(@PathVariable Long id) {
        return locationService.getMarkerResponseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/markers/region/{regionId}")
    public ResponseEntity<List<MarkerResponse>> getMarkersByRegionId(@PathVariable Long regionId) {
        List<MarkerResponse> markers = locationService.getMarkersByRegionId(regionId);
        return ResponseEntity.ok(markers);
    }

    @GetMapping("/markers/region/name/{regionName}")
    public ResponseEntity<List<MarkerResponse>> getMarkersByRegionName(@PathVariable String regionName) {
        List<MarkerResponse> markers = locationService.getMarkersByRegionName(regionName);
        return ResponseEntity.ok(markers);
    }

    @PostMapping("/regions")
    public ResponseEntity<RegionResponse> createRegion(@Valid @RequestBody CreateRegionRequest request) {
        RegionResponse saved = locationService.createRegion(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<RegionResponse>> getAllRegions() {
        List<RegionResponse> regions = locationService.getAllRegions();
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/regions/{id}")
    public ResponseEntity<RegionResponse> getRegionById(@PathVariable Long id) {
        return locationService.getRegionResponseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
