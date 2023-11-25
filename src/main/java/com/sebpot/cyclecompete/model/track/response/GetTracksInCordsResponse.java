package com.sebpot.cyclecompete.model.track.response;

import com.sebpot.cyclecompete.model.track.wrapper.GetTracksInCordsWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTracksInCordsResponse {
    private List<GetTracksInCordsWrapper> tracks;
}
