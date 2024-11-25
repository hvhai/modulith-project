package com.codehunter.modulithproject.countdown_timer.jpa_repository;

import com.codehunter.modulithproject.countdown_timer.jpa.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<JpaUser, String> {

}
