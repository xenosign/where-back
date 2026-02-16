package go.tetz.where_back.location.controller;

import go.tetz.where_back.location.entity.LocationMarker;
import go.tetz.where_back.location.entity.Region;
import go.tetz.where_back.location.service.LocationService;
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
    public ResponseEntity<LocationMarker> createMarker(@RequestBody LocationMarker marker) {
        LocationMarker savedMarker = locationService.saveMarker(marker);
        return ResponseEntity.ok(savedMarker);
    }

    @GetMapping("/markers")
    public ResponseEntity<List<LocationMarker>> getNearbyMarkers(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "0.5") Double radius) {

        List<LocationMarker> markers = locationService.getSurroundingMarkers(lat, lng, radius);
        return ResponseEntity.ok(markers);
    }

    @GetMapping("/markers/{id}")
    public ResponseEntity<LocationMarker> getMarkerDetail(@PathVariable Long id) {
        return locationService.getMarkerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/markers/region/{regionId}")
    public ResponseEntity<List<LocationMarker>> getMarkersByRegionId(@PathVariable Long regionId) {
        List<LocationMarker> markers = locationService.getMarkersByRegionId(regionId);
        return ResponseEntity.ok(markers);
    }

    @GetMapping("/markers/region/name/{regionName}")
    public ResponseEntity<List<LocationMarker>> getMarkersByRegionName(@PathVariable String regionName) {
        List<LocationMarker> markers = locationService.getMarkersByRegionName(regionName);
        return ResponseEntity.ok(markers);
    }

    @PostMapping("/regions")
    public ResponseEntity<Region> createRegion(@RequestBody Region region) {
        Region savedRegion = locationService.saveRegion(region);
        return ResponseEntity.ok(savedRegion);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> regions = locationService.getAllRegions();
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/regions/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        return locationService.getRegionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}