package com.sebpot.cyclecompete.model.track;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="point")
public class TrackPoint {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private int order;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;
}
