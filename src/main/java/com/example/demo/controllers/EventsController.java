package com.example.demo.controllers;

import com.example.demo.repositories.ContentAggregator;
import com.example.demo.repositories.EventTypeAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "http:// localhost:8080")
public class EventsController {

    private final ContentAggregator contentAggregator;
    private final EventTypeAggregator eventTypeAggregator;

    private final String CONTROLLER_NAME = "/events";


    @Autowired
    public EventsController(ContentAggregator contentAggregator, EventTypeAggregator eventTypeAggregator) {
        this.contentAggregator = contentAggregator;
        this.eventTypeAggregator = eventTypeAggregator;
    }

    @GetMapping(CONTROLLER_NAME + "/countByEventType")
    public ConcurrentHashMap<String, Long> countByEventType() {
        return this.eventTypeAggregator.getDataOccurrences();
    }

    @GetMapping(CONTROLLER_NAME + "/countWords")
    public ConcurrentHashMap<String, Long> countWords() {
        return this.contentAggregator.getDataOccurrences();
    }
}
