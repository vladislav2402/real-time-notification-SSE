package org.example.events;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class EventsMapper {

    public SseEmitter.SseEventBuilder toSseEventBuilder(EventsDto eventsDto) {
        return SseEmitter.event()
                .id(RandomStringUtils.randomAlphabetic(16))
                .name(eventsDto.eventType())
                .data(eventsDto.body());
    }

}
