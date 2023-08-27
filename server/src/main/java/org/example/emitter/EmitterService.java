package org.example.emitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class EmitterService {

    private final Long eventsConnectionTimeout;
    private final EmitterRepository emitterRepository;

    public EmitterService(@Value("${events.connection.timeout}") Long eventsConnectionTimeout,
                          EmitterRepository emitterRepository) {
        this.eventsConnectionTimeout = eventsConnectionTimeout;
        this.emitterRepository = emitterRepository;
    }

    public SseEmitter createEmitter(String emitterId) {
        SseEmitter sseEmitter = new SseEmitter(eventsConnectionTimeout);
        sseEmitter.onCompletion(() -> emitterRepository.removeEmitter(emitterId));
        sseEmitter.onError(e -> {
            emitterRepository.removeEmitter(emitterId);
            throw new IllegalStateException("Unable to create emitter for %s".formatted(emitterId));
        });
        emitterRepository.createOrUpdateEmitter(emitterId, sseEmitter);
        return sseEmitter;
    }

}
