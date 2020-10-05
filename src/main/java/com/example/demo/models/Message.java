package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * POJO that represents the data from the blackbox
 */
public class Message {

    private final String eventType;
    private final String data;
    private final Instant timestamp;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Message(@JsonProperty("event_type") String eventType, @JsonProperty("data") String data, @JsonProperty("timestamp") Timestamp timestamp) {
        this.eventType = eventType;
        this.data = data;
        this.timestamp = timestamp.toInstant();
    }

    public static Optional<Message> fromJson(String raw) {
        try {
            return Optional.of(new ObjectMapper().readValue(raw, Message.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String getEventType() {
        return eventType;
    }

    public String getData() {
        return data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "eventType='" + eventType + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
