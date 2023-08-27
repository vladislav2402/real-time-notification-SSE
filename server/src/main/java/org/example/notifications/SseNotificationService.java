package org.example.notifications;

import lombok.extern.slf4j.Slf4j;
import org.example.emitter.EmitterRepository;
import org.example.events.EventsDto;
import org.example.events.EventsMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SseNotificationService implements NotificationService {

    private final EmitterRepository emitterRepository;
    private final EventsMapper eventsMapper;

    public SseNotificationService(EmitterRepository emitterRepository,
                                  EventsMapper eventsMapper) {
        this.emitterRepository = emitterRepository;
        this.eventsMapper = eventsMapper;
    }

    @Override
    public void sendNotification(String memberId, EventsDto event) {
        if (event == null) {
            return;
        }
        doSendNotification(memberId, event);
    }

    private void doSendNotification(String memberId, EventsDto eventsDto) {
        emitterRepository.getEmitter(memberId).ifPresent(sseEmitter -> {
            try {
                log.info("Sending event {} to {}", eventsDto, memberId);
                sseEmitter.send(eventsMapper.toSseEventBuilder(eventsDto));
            } catch (Exception e) {
                log.warn("Unable to send event {} for member {}", eventsDto, memberId, e);
                emitterRepository.removeEmitter(memberId);
            }
        });
    }


}
