package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.track.*;
import com.sebpot.cyclecompete.model.track.request.CreateTrackRequest;
import com.sebpot.cyclecompete.model.track.request.CreateTrackRunRequest;
import com.sebpot.cyclecompete.model.track.request.GetTracksRequest;
import com.sebpot.cyclecompete.model.track.response.GetTrackResponse;
import com.sebpot.cyclecompete.model.track.response.GetTrackRunsOfTrackResponse;
import com.sebpot.cyclecompete.model.track.response.GetTrackRunsOfUserResponse;
import com.sebpot.cyclecompete.model.track.response.GetTracksResponse;
import com.sebpot.cyclecompete.model.track.wrapper.*;
import com.sebpot.cyclecompete.repository.TrackPointRepository;
import com.sebpot.cyclecompete.repository.TrackRepository;
import com.sebpot.cyclecompete.repository.TrackRunRepository;
import com.sebpot.cyclecompete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final TrackPointRepository trackPointRepository;
    private final TrackRunRepository trackRunRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void createNewTrack(CreateTrackRequest request, String token) throws Exception {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Current user does not exist."));

        var track = trackRepository.findByCords(request.getStartLongitude(), request.getStartLatitude());
        if(track.isPresent())
            throw new Exception("Track with given starting point already exists.");

        var newTrack = Track.builder()
                .creator(user)
                .name(request.getName().strip())
                .description(request.getDescription().strip())
                .startLongitude(request.getStartLongitude())
                .startLatitude(request.getStartLatitude())
                .build();
        trackRepository.save(newTrack);

        for(TrackPointWrapper pointWrapper : request.getTrackPoints()){
            var point = TrackPoint.builder()
                    .longitude(pointWrapper.getLongitude())
                    .latitude(pointWrapper.getLatitude())
                    .sequencePosition(pointWrapper.getSequencePosition())
                    .track(newTrack)
                    .build();
            trackPointRepository.save(point);
        }
    }

    public GetTracksResponse getAllTracksIncludedInCords(GetTracksRequest request) {
        var tracks = trackRepository.findAllIncludedInCords(request.getTopLeftLongitude(), request.getTopLeftLatitude(), request.getBottomRightLongitude(), request.getBottomRightLatitude());

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

    public GetTrackResponse getTrack(int id) throws Exception {
        var track = trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Track does not exist."));

        var trackPoints = trackPointRepository.findAllByTrackId(track.getId());
        if(trackPoints.isEmpty())
            throw new IllegalArgumentException("Track does not contain any points.");

        List<TrackPointWrapper> trackPointWrappers = new ArrayList<>();
        for(TrackPoint trackPoint : trackPoints){
            trackPointWrappers.add(TrackPointWrapper.builder()
                    .latitude(trackPoint.getLatitude())
                    .longitude(trackPoint.getLongitude())
                    .sequencePosition(trackPoint.getSequencePosition())
                    .build());
        }

        return GetTrackResponse.builder()
                .id(track.getId())
                .userFirstname(track.getCreator().getFirstname())
                .userLastname(track.getCreator().getLastname())
                .name(track.getName())
                .startLatitude(track.getStartLatitude())
                .startLongitude(track.getStartLongitude())
                .averageTime(track.getAverageTime())
                .trackPoints(trackPointWrappers)
                .build();
    }

    public void deleteTrack(int id, String token) throws Exception {
        var track = trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Track does not exist."));

        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Current user does not exist."));
        if(user.getId() != track.getCreator().getId())
            throw new Exception("Unable to delete given track, you are not the creator.");

        var trackPoints = trackPointRepository.findAllByTrackId(id);
        for(TrackPoint trackPoint : trackPoints){
            trackPointRepository.delete(trackPoint);
        }

        trackRepository.delete(track);
    }

    public void createNewTrackRun(CreateTrackRunRequest request, String token) {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Current user does not exist."));

        var track = trackRepository.findById(request.getTrackId())
                .orElseThrow(() -> new IllegalArgumentException("Given track does not exist."));

        var hours = request.getEndDate().getHour() - request.getStartDate().getHour();
        var minutes = (request.getEndDate().getMinute() - request.getStartDate().getMinute());
        if(hours > 0 && minutes < 0){
            hours -= 1;
            minutes += 60;
        }
        var seconds = (request.getEndDate().getSecond() - request.getStartDate().getSecond());
        if(minutes > 0 && seconds < 0){
            minutes -= 1;
            seconds += 60;
        }
        LocalTime duration = LocalTime.of(hours, minutes, seconds);

        var trackRun = TrackRun.builder()
                .track(track)
                .user(user)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .duration(duration)
                .build();
        trackRunRepository.save(trackRun);
    }

    public GetTrackRunsOfTrackResponse getAllTrackRunsOfTrack(int id) {
        var track = trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Given track does not exist."));

        var trackRuns = trackRunRepository.findAllBestByTrack(track);

        List<TrackRunsOfTrackWrapper> trackRunWrappers = new ArrayList<>();
        for(TrackRunQueryWrapper trackRun : trackRuns){
            trackRunWrappers.add(TrackRunsOfTrackWrapper.builder()
                    .userFirstname(trackRun.getUser().getFirstname())
                    .userLastname(trackRun.getUser().getLastname())
                    .duration(trackRun.getMinDuration())
                    .build()
            );
        }

        return GetTrackRunsOfTrackResponse.builder()
                .trackRuns(trackRunWrappers)
                .build();
    }

    public GetTrackRunsOfUserResponse getAllTrackRunsOfUser(String token) {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Current user does not exist."));

        var trackRuns = trackRunRepository.findAllByUser(user);

        List<TrackRunsOfUserWrapper> trackRunWrappers = new ArrayList<>();
        for(TrackRun trackRun : trackRuns){
            trackRunWrappers.add(TrackRunsOfUserWrapper.builder()
                    .trackName(trackRun.getTrack().getName())
                    .endDate(trackRun.getEndDate())
                    .duration(trackRun.getDuration())
                    .build()
            );
        }

        return GetTrackRunsOfUserResponse.builder()
                .trackRuns(trackRunWrappers)
                .build();
    }
}
