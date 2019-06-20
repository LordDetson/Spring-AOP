package by.babanin.logger;

import by.babanin.model.Event;
import org.springframework.stereotype.Component;

@Component
public class ConsoleLogger implements EventLogger {
    @Override
    public void logEvent(Event event) {
        System.out.println(event);
    }
}
