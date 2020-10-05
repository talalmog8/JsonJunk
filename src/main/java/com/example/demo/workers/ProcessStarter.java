package com.example.demo.workers;

import com.example.demo.processors.DataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Starts the processor
 */
@Component
public class ProcessStarter implements CommandLineRunner {

    private final DataProcessor dataProcessor;

    @Autowired
    public ProcessStarter(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    @Override
    public void run(String... args) {
        dataProcessor.startProcessing();
        dataProcessor.dispose();
    }
}
