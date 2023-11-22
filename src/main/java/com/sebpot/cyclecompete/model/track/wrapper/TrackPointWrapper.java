package com.sebpot.cyclecompete.model.track.wrapper;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackPointWrapper {
    private double longitude;
    private double latitude;
    private int sequencePosition;
}
