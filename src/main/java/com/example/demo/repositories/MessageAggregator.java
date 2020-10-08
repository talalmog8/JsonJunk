package com.example.demo.repositories;

import com.example.demo.models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MessageAggregator extends DisposableObserver<Optional<Message>> {
    private final Logger logger = LoggerFactory.getLogger(MessageAggregator.class);
    private final ConcurrentHashMap<String, Long> dataOccurrences;

    public MessageAggregator(ConcurrentHashMap<String, Long> dataOccurrences) {
        this.dataOccurrences = dataOccurrences;
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        logger.info("error in subscription: {0}", throwable);
    }

    public void mergeWith(String data) {
        dataOccurrences.merge(data, 1L, Long::sum);
    }

    @Override
    public void onComplete() {
        logger.info("complected subscription");
    }

    @Override
    public String toString() {
        String content = "[]";
        try {
            content = "\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dataOccurrences);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return content;
    }

    public ConcurrentHashMap<String, Long> getDataOccurrences() {
        return dataOccurrences;
    }
}
