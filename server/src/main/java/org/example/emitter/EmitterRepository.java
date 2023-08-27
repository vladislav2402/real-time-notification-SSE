package org.example.emitter;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

public interface EmitterRepository {

    void createOrUpdateEmitter(String emitterId, SseEmitter sseEmitter);

    void removeEmitter(String emitterId);

    Optional<SseEmitter> getEmitter(String emitterId);

}
