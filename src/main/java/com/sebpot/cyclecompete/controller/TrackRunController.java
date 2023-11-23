package com.sebpot.cyclecompete.controller;

import com.sebpot.cyclecompete.model.track.request.CreateTrackRunRequest;
import com.sebpot.cyclecompete.model.track.response.GetTrackRunsOfTrackResponse;
import com.sebpot.cyclecompete.model.track.response.GetTrackRunsOfUserResponse;
import com.sebpot.cyclecompete.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/run")
@RequiredArgsConstructor
public class TrackRunController {

    private final TrackService trackService;

    @PostMapping("")
    public ResponseEntity<?> createTrackRun(
            @RequestBody CreateTrackRunRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        trackService.createNewTrackRun(request, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/track/{id}")
    public ResponseEntity<GetTrackRunsOfTrackResponse> getTrackRunsOfTrack(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(trackService.getAllTrackRunsOfTrack(id));
    }

    @GetMapping("/user")
    public ResponseEntity<GetTrackRunsOfUserResponse> getTrackRunsOfUser(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(trackService.getAllTrackRunsOfUser(token));
    }
}
