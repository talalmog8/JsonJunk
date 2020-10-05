package com.example.demo.processors;

import com.example.demo.models.Message;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Implementation of the json processing using rxjava
 */
@Component
public class BasicProcessor implements DataProcessor {
    private final ConnectableObservable<Optional<Message>> observable;
    private final List<DisposableObserver<Optional<Message>>> subscribers;
    private final String DATA_DELIMITER = "\\n";

    /**
     * @param subscribers all the subscribers for json processing. more subscribers can be added in the future without this class changing.
     */
    public BasicProcessor(List<DisposableObserver<Optional<Message>>> subscribers, @Value("${app.location}") String executablePath) {
        observable = Observable
                .using(() -> Runtime.getRuntime().exec(executablePath)
                        , process -> Observable.fromIterable(() -> new Scanner(process.getInputStream()).useDelimiter(DATA_DELIMITER))
                        , Process::destroy
                )
                .map(Message::fromJson)
                .filter(Optional::isPresent)
                .share()
                .replay();

        this.subscribers = subscribers.stream().map(observable::subscribeWith).collect(Collectors.toList());
    }

    @Override
    public void startProcessing() {
        observable.connect();
    }

    @Override
    public void dispose() {
        subscribers.forEach(Disposable::dispose);
    }
}
