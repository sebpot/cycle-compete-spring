package com.sebpot.cyclecompete.repository;

import com.sebpot.cyclecompete.model.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {

}
