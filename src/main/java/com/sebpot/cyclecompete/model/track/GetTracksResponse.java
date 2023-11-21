package com.sebpot.cyclecompete.model.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTracksResponse {
    private List<GetTracksWrapper> tracks;
}
