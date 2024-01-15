package com.sebpot.cyclecompete.model.track;


import com.sebpot.cyclecompete.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="track")
public class Track {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = true)
    private User creator;

    private String name;
    private String description;

    @Column(name = "start_longitude", nullable = false)
    private double startLongitude;

    @Column(name = "start_latitude", nullable = false)
    private double startLatitude;

    private LocalTime averageTime;

    private String privacy;
}
