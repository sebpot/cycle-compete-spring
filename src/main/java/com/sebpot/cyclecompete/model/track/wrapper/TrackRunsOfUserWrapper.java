package com.sebpot.cyclecompete.model.track.wrapper;

import jakarta.persistence.Column;
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
public class TrackRunsOfUserWrapper {
    private Integer id;
    private String trackName;
    private LocalDateTime endDate;
    private String duration;
}
