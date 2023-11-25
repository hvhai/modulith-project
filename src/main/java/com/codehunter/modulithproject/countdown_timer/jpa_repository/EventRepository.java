package com.codehunter.modulithproject.countdown_timer.jpa_repository;

import com.codehunter.modulithproject.countdown_timer.jpa.JpaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<JpaEvent, Long> {
    @Query("select e from JpaEvent e where e.user.id = :userId")
    List<JpaEvent> findByUserId(@Param("userId") String userId);
    @Query("select e from JpaEvent e where e.id = :eventId and e.user.id = :userId")
    JpaEvent findByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") String userId);

}
