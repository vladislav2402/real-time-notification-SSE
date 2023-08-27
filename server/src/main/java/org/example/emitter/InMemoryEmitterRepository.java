package org.example.emitter;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryEmitterRepository implements EmitterRepository {

    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    @Override
    public void createOrUpdateEmitter(String emitterId, SseEmitter sseEmitter) {
        emitterMap.put(emitterId, sseEmitter);
    }

    @Override
    public void removeEmitter(String emitterId) {
        emitterMap.remove(emitterId);
    }

    @Override
    public Optional<SseEmitter> getEmitter(String emitterId) {
        return Optional.ofNullable(emitterMap.get(emitterId));
    }
}
