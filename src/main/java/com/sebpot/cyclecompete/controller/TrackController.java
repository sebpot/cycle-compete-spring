package com.sebpot.cyclecompete.controller;

import com.sebpot.cyclecompete.model.track.CreateTrackRequest;
import com.sebpot.cyclecompete.model.track.GetTracksResponse;
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
    public ResponseEntity<GetTracksResponse> getTracks() {
        return ResponseEntity.ok(trackService.getAllTracks());
    }
}
