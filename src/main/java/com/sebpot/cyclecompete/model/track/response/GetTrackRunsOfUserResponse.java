package com.sebpot.cyclecompete.model.track.response;

import com.sebpot.cyclecompete.model.track.wrapper.TrackRunsOfUserWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTrackRunsOfUserResponse {
    private List<TrackRunsOfUserWrapper> trackRuns;
}
