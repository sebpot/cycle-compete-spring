package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.track.*;
import com.sebpot.cyclecompete.repository.TrackPointRepository;
import com.sebpot.cyclecompete.repository.TrackRepository;
import com.sebpot.cyclecompete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final TrackPointRepository trackPointRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void createNewTrack(CreateTrackRequest request, String token) throws Exception {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        var track = Track.builder()
                .creator(user)
                .name(request.getName().strip())
                .description(request.getDescription().strip())
                .startLongitude(request.getStartLongitude())
                .startLatitude(request.getStartLatitude())
                .build();
        trackRepository.save(track);

        for(TrackPointWrapper pointWrapper : request.getTrackPoints()){
            var point = TrackPoint.builder()
                    .longitude(pointWrapper.getLongitude())
                    .latitude(pointWrapper.getLatitude())
                    .sequencePosition(pointWrapper.getSequencePosition())
                    .track(track)
                    .build();
            trackPointRepository.save(point);
        }

    }

    public GetTracksResponse getAllTracks() {
        var tracks = trackRepository.findAll();

        List<GetTracksWrapper> trackWrappers = new ArrayList<>();
        for(Track track : tracks){
            trackWrappers.add(GetTracksWrapper.builder()
                    .id(track.getId())
                    .userFirstname(track.getCreator().getFirstname())
                    .userLastname(track.getCreator().getLastname())
                    .name(track.getName())
                    .startLatitude(track.getStartLatitude())
                    .startLongitude(track.getStartLongitude())
                    .averageTime(track.getAverageTime())
                    .build());
        }

        return GetTracksResponse.builder()
                .tracks(trackWrappers)
                .build();
    }
}
