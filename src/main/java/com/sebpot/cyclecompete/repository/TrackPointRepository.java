package com.sebpot.cyclecompete.repository;

import com.sebpot.cyclecompete.model.track.TrackPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackPointRepository extends JpaRepository<TrackPoint, Integer> {
}
