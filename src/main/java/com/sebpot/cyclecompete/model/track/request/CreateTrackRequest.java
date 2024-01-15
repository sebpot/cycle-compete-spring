package com.sebpot.cyclecompete.model.track.request;

import com.sebpot.cyclecompete.model.track.wrapper.TrackPointWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrackRequest {
    private String name;
    private String description;
    private double startLongitude;
    private double startLatitude;
    private String privacy;
    private List<TrackPointWrapper> trackPoints;
}
