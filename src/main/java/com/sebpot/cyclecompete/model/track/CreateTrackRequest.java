package com.sebpot.cyclecompete.model.track;

import jakarta.persistence.Column;
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
    private List<TrackPointWrapper> trackPoints;
}
