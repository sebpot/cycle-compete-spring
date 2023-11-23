package com.sebpot.cyclecompete.model.track.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrackRunRequest {
    private Integer trackId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
