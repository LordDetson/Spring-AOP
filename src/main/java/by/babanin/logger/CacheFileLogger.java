package by.babanin.logger;

import by.babanin.model.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component("defaultLogger")
public class CacheFileLogger extends FileLogger {
    private int cacheSize;
    private List<Event> events;

    public CacheFileLogger(
            @Value("${filename}") String filename,
            @Value("${cacheSize}") int cacheSize
    ) {
        super(filename);
        this.cacheSize = cacheSize;
        events = new ArrayList<>(cacheSize);
    }

    @Override
    public void logEvent(Event event) {
        events.add(event);
        if (cacheSize == events.size()) {
            writeAndClearCache();
        }
    }

    private void writeAndClearCache() {
        for (Event event1 : events) {
            super.logEvent(event1);
        }
        events.clear();
    }

    @PreDestroy
    private void destroy() {
        if (!events.isEmpty()) {
            writeAndClearCache();
        }
    }
}
