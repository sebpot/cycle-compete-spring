package com.sebpot.cyclecompete.model.track.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackRunsOfTrackWrapper {
    private String userFirstname;
    private String userLastname;
    private LocalTime duration;
}
