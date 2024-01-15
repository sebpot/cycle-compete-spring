package com.sebpot.cyclecompete.controller;

import com.sebpot.cyclecompete.model.track.request.CreateTrackRequest;
import com.sebpot.cyclecompete.model.track.response.GetClosestTracksResponse;
import com.sebpot.cyclecompete.model.track.response.GetTrackResponse;
import com.sebpot.cyclecompete.model.track.response.GetTracksInCordsResponse;
import com.sebpot.cyclecompete.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping("")
    public ResponseEntity<?> createTrack(
            @RequestBody CreateTrackRequest request,
            @RequestHeader("Authorization") String authHeader
    ) throws Exception {
        String token = authHeader.substring(7);
        trackService.createNewTrack(request, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<GetTracksInCordsResponse> getTracksIncludedInCords(
            @RequestParam("tlLng") double topLeftLongitude,
            @RequestParam("tlLat") double topLeftLatitude,
            @RequestParam("brLng") double bottomRightLongitude,
            @RequestParam("brLat") double bottomRightLatitude,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(trackService.getAllTracksIncludedInCords(
                topLeftLongitude,
                topLeftLatitude,
                bottomRightLongitude,
                bottomRightLatitude,
                token
        ));
    }

    @GetMapping("/closest")
    public ResponseEntity<GetClosestTracksResponse> getNumberOfClosestTracks(
            @RequestParam("n") int n,
            @RequestParam("lon") double longitude,
            @RequestParam("lat") double latitude,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(trackService.getClosestTracks(n, longitude, latitude, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTrackResponse> getTrack(
            @PathVariable int id
    ) throws Exception {
        return ResponseEntity.ok(trackService.getTrack(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(
            @PathVariable int id,
            @RequestHeader("Authorization") String authHeader
    ) throws Exception {
        String token = authHeader.substring(7);
        trackService.deleteTrack(id, token);
        return ResponseEntity.ok().build();
    }
}
