package com.sebpot.cyclecompete.model.track.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetClosestTracksWrapper {
    private Integer id;
    private Integer creatorId;
    private String userFirstname;
    private String userLastname;
    private String name;
    private String averageTime;
    private String privacy;
    private double distanceTo;
}
