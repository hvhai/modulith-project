package com.codehunter.modulithproject.countdown_timer.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "event")
@Setter
@Getter
public class JpaEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "time")
    private Instant publicTime;
    @Column(columnDefinition = "varchar(25) default 'CREATED'")
    @Enumerated(EnumType.STRING)
    private JpaEventStatusType status = JpaEventStatusType.CREATED;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private JpaUser user;

}
