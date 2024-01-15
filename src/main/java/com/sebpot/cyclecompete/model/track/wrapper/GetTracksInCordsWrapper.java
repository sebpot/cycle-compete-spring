package com.sebpot.cyclecompete.model.track.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTracksInCordsWrapper {
    private Integer id;
    private Integer creatorId;
    private String userFirstname;
    private String userLastname;
    private String name;
    private double startLongitude;
    private double startLatitude;
    private String averageTime;
    private String privacy;
}
