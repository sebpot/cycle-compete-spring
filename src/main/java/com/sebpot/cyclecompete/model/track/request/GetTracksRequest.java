package com.sebpot.cyclecompete.model.track.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTracksRequest {
    private double topLeftLongitude;
    private double topLeftLatitude;
    private double bottomRightLongitude;
    private double bottomRightLatitude;
}
