package com.sebpot.cyclecompete.model.track.wrapper;

import com.sebpot.cyclecompete.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackRunQueryWrapper {
    private User user;
    private LocalTime minDuration;
}
