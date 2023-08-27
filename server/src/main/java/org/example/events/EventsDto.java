package org.example.events;


import java.util.Map;

public record EventsDto(String eventType, Map<String, Object> body) {

}
