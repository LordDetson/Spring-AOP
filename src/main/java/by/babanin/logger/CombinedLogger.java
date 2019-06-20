package by.babanin.logger;

import by.babanin.model.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CombinedLogger implements EventLogger {
    private Collection<EventLogger> eventLoggers;

    public CombinedLogger(
            @Qualifier("eventLoggers") Collection<EventLogger> eventLoggers
    ) {
        this.eventLoggers = eventLoggers;
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger eventLogger : eventLoggers) {
            eventLogger.logEvent(event);
        }
    }
}
