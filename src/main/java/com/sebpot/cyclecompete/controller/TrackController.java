package com.sebpot.cyclecompete.controller;

import com.sebpot.cyclecompete.model.track.request.CreateTrackRequest;
import com.sebpot.cyclecompete.model.track.response.GetTrackResponse;
import com.sebpot.cyclecompete.model.track.request.GetTracksRequest;
import com.sebpot.cyclecompete.model.track.response.GetTracksResponse;
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
    public ResponseEntity<GetTracksResponse> getTracksIncludedInCords(
            @RequestBody GetTracksRequest request
    ) {
        return ResponseEntity.ok(trackService.getAllTracksIncludedInCords(request));
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
