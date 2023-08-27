package org.example.events;

import lombok.extern.slf4j.Slf4j;
import org.example.emitter.EmitterService;
import org.example.notifications.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequestMapping("/api/events")
@RestController
public class EventsController {

    private static final String MEMBER_ID_HEADER = "MemberId";

    private final NotificationService notificationService;
    private final EmitterService emitterService;

    public EventsController(NotificationService notificationService,
                            EmitterService emitterService) {
        this.notificationService = notificationService;
        this.emitterService = emitterService;
    }

    @GetMapping
    public SseEmitter subscribeOnEvents(@RequestHeader(name = MEMBER_ID_HEADER) String memberId) {
        log.info("{} subscribed on events", memberId);
        return emitterService.createEmitter(memberId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void publishEvent(@RequestHeader(name = MEMBER_ID_HEADER) String memberId,
                             @RequestBody EventsDto eventsDto) {
        log.info("Publishing event {} to {}", eventsDto, memberId);
        notificationService.sendNotification(memberId, eventsDto);
    }

}
