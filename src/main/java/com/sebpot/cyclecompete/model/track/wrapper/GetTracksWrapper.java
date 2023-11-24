package com.sebpot.cyclecompete.model.track.wrapper;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTracksWrapper {
    private Integer id;
    private String userFirstname;
    private String userLastname;
    private String name;
    private double startLongitude;
    private double startLatitude;
    private String averageTime;
}
