package com.sebpot.cyclecompete.repository;

import com.sebpot.cyclecompete.model.track.Track;
import com.sebpot.cyclecompete.model.track.TrackRun;
import com.sebpot.cyclecompete.model.track.wrapper.TrackRunQueryWrapper;
import com.sebpot.cyclecompete.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRunRepository extends JpaRepository<TrackRun, Integer> {

    @Query("SELECT new com.sebpot.cyclecompete.model.track.wrapper.TrackRunQueryWrapper(tR.user, MIN(tR.duration) AS minDuration) FROM TrackRun tR WHERE track = :id_of_track GROUP BY user ORDER BY minDuration ASC")
    List<TrackRunQueryWrapper> findAllBestByTrack(@Param("id_of_track") Track track);

    @Query("SELECT tR FROM TrackRun tR WHERE user = :id_of_user ORDER BY endDate DESC")
    List<TrackRun> findAllByUser(@Param("id_of_user") User user);
}
