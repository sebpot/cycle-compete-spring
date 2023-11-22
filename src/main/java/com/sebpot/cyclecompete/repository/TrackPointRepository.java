package com.sebpot.cyclecompete.repository;

import com.sebpot.cyclecompete.model.track.TrackPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackPointRepository extends JpaRepository<TrackPoint, Integer> {
    List<TrackPoint> findAllByTrackId(Integer id);
}
