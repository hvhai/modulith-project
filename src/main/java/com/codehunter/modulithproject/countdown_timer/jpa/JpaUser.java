package com.codehunter.modulithproject.countdown_timer.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countdown_app_user")
@Setter
@Getter
public class JpaUser {
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "user")
    private List<JpaEvent> eventList = new ArrayList<>();
}
