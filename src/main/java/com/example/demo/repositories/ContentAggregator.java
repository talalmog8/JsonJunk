package com.example.demo.repositories;

import com.example.demo.models.Message;
import io.reactivex.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Aggregates the statistics of the 'data' field in the json
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ContentAggregator extends MessageAggregator {
    private final Logger logger = LoggerFactory.getLogger(ContentAggregator.class);

    public ContentAggregator(ConcurrentHashMap<String, Long> contentOccurrences) {
        super(contentOccurrences);
    }

    public ContentAggregator() {
        super(new ConcurrentHashMap<>());
    }

    @Override
    public void onNext(@NonNull Optional<Message> message) {
        message.ifPresent(m -> mergeWith(m.getData()));
        logger.info(toString());
    }
}
