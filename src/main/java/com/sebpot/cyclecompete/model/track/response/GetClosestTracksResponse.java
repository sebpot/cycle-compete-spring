package com.sebpot.cyclecompete.model.track.response;

import com.sebpot.cyclecompete.model.track.wrapper.GetClosestTracksWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetClosestTracksResponse {
    private List<GetClosestTracksWrapper> tracks;
}
