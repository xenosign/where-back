package go.tetz.where_back.location.controller;

import go.tetz.where_back.location.dto.CreateMarkerRequest;
import go.tetz.where_back.location.dto.CreateRegionRequest;
import go.tetz.where_back.location.dto.MarkerResponse;
import go.tetz.where_back.location.dto.RegionResponse;
import go.tetz.where_back.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "위치", description = "마커 및 지역 API")
@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "마커 생성")
    @PostMapping("/markers")
    public ResponseEntity<MarkerResponse> createMarker(@Valid @RequestBody CreateMarkerRequest request) {
        MarkerResponse saved = locationService.createMarker(request);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "근처 마커 조회", description = "위도/경도 기준 반경 내 마커 목록")
    @GetMapping("/markers")
    public ResponseEntity<List<MarkerResponse>> getNearbyMarkers(
            @Parameter(description = "위도") @RequestParam Double lat,
            @Parameter(description = "경도") @RequestParam Double lng,
            @Parameter(description = "반경(km)") @RequestParam(defaultValue = "0.5") Double radius) {
        List<MarkerResponse> markers = locationService.getSurroundingMarkerResponses(lat, lng, radius);
        return ResponseEntity.ok(markers);
    }

    @Operation(summary = "마커 상세 조회")
    @ApiResponse(responseCode = "404", description = "마커 없음")
    @GetMapping("/markers/{id}")
    public ResponseEntity<MarkerResponse> getMarkerDetail(
            @Parameter(description = "마커 ID") @PathVariable Long id) {
        return locationService.getMarkerResponseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "지역별 마커 조회")
    @GetMapping("/markers/region/{regionId}")
    public ResponseEntity<List<MarkerResponse>> getMarkersByRegionId(
            @Parameter(description = "지역 ID") @PathVariable Long regionId) {
        List<MarkerResponse> markers = locationService.getMarkersByRegionId(regionId);
        return ResponseEntity.ok(markers);
    }

    @Operation(summary = "지역명별 마커 조회")
    @GetMapping("/markers/region/name/{regionName}")
    public ResponseEntity<List<MarkerResponse>> getMarkersByRegionName(
            @Parameter(description = "지역명") @PathVariable String regionName) {
        List<MarkerResponse> markers = locationService.getMarkersByRegionName(regionName);
        return ResponseEntity.ok(markers);
    }

    @Operation(summary = "지역 생성")
    @PostMapping("/regions")
    public ResponseEntity<RegionResponse> createRegion(@Valid @RequestBody CreateRegionRequest request) {
        RegionResponse saved = locationService.createRegion(request);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "전체 지역 목록")
    @GetMapping("/regions")
    public ResponseEntity<List<RegionResponse>> getAllRegions() {
        List<RegionResponse> regions = locationService.getAllRegions();
        return ResponseEntity.ok(regions);
    }

    @Operation(summary = "지역 상세 조회")
    @ApiResponse(responseCode = "404", description = "지역 없음")
    @GetMapping("/regions/{id}")
    public ResponseEntity<RegionResponse> getRegionById(
            @Parameter(description = "지역 ID") @PathVariable Long id) {
        return locationService.getRegionResponseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
