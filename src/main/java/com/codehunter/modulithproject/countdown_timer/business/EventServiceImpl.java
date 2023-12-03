package com.codehunter.modulithproject.countdown_timer.business;


import com.codehunter.modulithproject.countdown_timer.domain.Event;
import com.codehunter.modulithproject.countdown_timer.domain.User;
import com.codehunter.modulithproject.countdown_timer.jpa.JpaEvent;
import com.codehunter.modulithproject.countdown_timer.jpa.JpaUser;
import com.codehunter.modulithproject.countdown_timer.jpa_repository.EventRepository;
import com.codehunter.modulithproject.countdown_timer.jpa_repository.UserRepository;
import com.codehunter.modulithproject_lib.common.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Event create(Event event) {
        if (event.getHost() == null) {
            log.warn("Cannot create event - empty user");
            return null;
        }

        JpaUser existedUser = userRepository.findById(event.getHost().getId())
                .orElse(createNewUser(event.getHost()));

        JpaEvent jpaEvent = new JpaEvent();
        jpaEvent.setName(event.getName());
        jpaEvent.setPublicTime(event.getDate().toInstant());
        jpaEvent.setUser(existedUser);
        JpaEvent newJpaEvent = eventRepository.save(jpaEvent);
        return toEvent(newJpaEvent);
    }

    public List<Event> getAll() {
        List<JpaEvent> allJpaEvent = eventRepository.findAll();
        return allJpaEvent.stream().map(EventServiceImpl::toEvent).toList();
    }

    public List<Event> getAllByUser(User user) {
        List<JpaEvent> allJpaEvent = eventRepository.findByUserId(user.getId());
        return allJpaEvent.stream().map(EventServiceImpl::toEvent).toList();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    private JpaUser createNewUser(User user) {
        JpaUser jpaUser = new JpaUser();
        jpaUser.setId(user.getId());
        jpaUser.setName(user.getName());
        jpaUser.setEventList(Collections.emptyList());
        return userRepository.save(jpaUser);
    }

    private static Event toEvent(JpaEvent newJpaEvent) {
        return Event.builder()
                .id(new Event.EventId(newJpaEvent.getId()))
                .name(newJpaEvent.getName())
                .date(newJpaEvent.getPublicTime().atZone(ZoneOffset.UTC))
                .build();
    }

    public Event update(Event event) {
        Long id = event.getId().getValue();
        JpaEvent jpaEvent = eventRepository.findByEventIdAndUserId(id, event.getHost().getId());
        if (jpaEvent == null)
            throw new IdNotFoundException(id.toString());
        jpaEvent.setName(event.getName());
        jpaEvent.setPublicTime(event.getDate().toInstant());
        JpaEvent save = eventRepository.save(jpaEvent);
        return toEvent(save);
    }
}
