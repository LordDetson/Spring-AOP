package by.babanin;

import by.babanin.logger.EventLogger;
import by.babanin.logger.RuDateFormatSymbols;
import by.babanin.model.Event;
import by.babanin.model.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;

import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class AppConfig {

    @Bean
    SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("EEE, d MMMMMMMM yyyy HH:mm:ss Z", new RuDateFormatSymbols());
    }

    @Bean
    @Scope("prototype")
    Event event(SimpleDateFormat simpleDateFormat) {
        return new Event(new Date(), simpleDateFormat);
    }

    @Bean
    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer pphc = new PropertyPlaceholderConfigurer();
        pphc.setIgnoreResourceNotFound(true);
        pphc.setLocation(new ClassPathResource("client.properties"));
        return pphc;
    }

    @Bean
    Map<EventType, EventLogger> loggerMap(
            EventLogger consoleLogger,
            EventLogger combinedLogger) {
        Map<EventType, EventLogger> loggerMap = new HashMap<>();
        loggerMap.put(EventType.INFO, consoleLogger);
        loggerMap.put(EventType.ERROR, combinedLogger);
        return loggerMap;
    }

    @Bean
    Collection<EventLogger> eventLoggers(
            EventLogger consoleLogger,
            EventLogger fileLogger
    ) {
        List<EventLogger> eventLoggers = new ArrayList<>();
        eventLoggers.add(fileLogger);
        eventLoggers.add(consoleLogger);
        return eventLoggers;
    }

}
