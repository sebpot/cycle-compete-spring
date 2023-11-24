package com.sebpot.cyclecompete.repository;

import com.sebpot.cyclecompete.model.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {

    @Query("SELECT t FROM Track t WHERE (startLongitude > :tlLo AND startLongitude < :brLo AND startLatitude < :tlLa AND startLatitude > :brLa)")
    List<Track> findAllIncludedInCords(
            @Param("tlLo") double topLeftLongitude,
            @Param("tlLa") double topLeftLatitude,
            @Param("brLo") double bottomRightLongitude,
            @Param("brLa") double bottomRightLatitude);

    @Query("SELECT t FROM Track t WHERE startLongitude = :sLo AND startLatitude = :sLa")
    Optional<Track> findByCords(
            @Param("sLo") double startLongitude,
            @Param("sLa") double startLatitude);
}
