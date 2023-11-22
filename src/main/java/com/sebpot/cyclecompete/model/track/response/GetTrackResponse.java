package com.sebpot.cyclecompete.model.track.response;

import com.sebpot.cyclecompete.model.track.wrapper.TrackPointWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTrackResponse {
    private Integer id;
    private String userFirstname;
    private String userLastname;
    private String name;
    private String description;
    private double startLongitude;
    private double startLatitude;
    private LocalTime averageTime;
    private List<TrackPointWrapper> trackPoints;
}
