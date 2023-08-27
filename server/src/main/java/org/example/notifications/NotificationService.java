package org.example.notifications;

import org.example.events.EventsDto;

public interface NotificationService {

    void sendNotification(String memberId, EventsDto event);

}
