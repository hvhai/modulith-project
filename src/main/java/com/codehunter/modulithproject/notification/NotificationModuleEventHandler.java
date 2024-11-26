package com.codehunter.modulithproject.notification;

import com.codehunter.modulithproject.shared.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationModuleEventHandler {

    @ApplicationModuleListener
    public void onNotificationEvent(NotificationEvent event) {
        log.info("On NotificationEvent, orderId={}, notificationEventType={}, message={}",
                event.orderId(), event.notificationEventType(), event.message());
    }
}
