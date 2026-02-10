package go.tetz.where_back.location.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    @GetMapping("")
    public ResponseEntity<?> locationHome() {
        return ResponseEntity.ok(
                "location controller"
        );
    }
}
