package by.babanin;

import by.babanin.logger.EventLogger;
import by.babanin.logger.StatisticsAspect;
import by.babanin.model.Client;
import by.babanin.model.Event;
import by.babanin.model.EventType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Runner {
    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggers;
    private StatisticsAspect statisticsAspect;

    public Runner(
            Client client,
            EventLogger defaultLogger,
            Map<EventType, EventLogger> loggerMap,
            StatisticsAspect statisticsAspect) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggerMap;
        this.statisticsAspect = statisticsAspect;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext("context.xml");
        Runner app = ac.getBean(Runner.class);
        Event event;
        int i = 0;
        while (i < 10) {
            event = ac.getBean(Event.class);
            event.setMessage("Some event for " + i++);
            app.logEvent(null, event);
        }
        while (i < 20) {
            event = ac.getBean(Event.class);
            event.setMessage("Some event for " + i++);
            app.logEvent(EventType.INFO, event);
        }
        while (i < 30) {
            event = ac.getBean(Event.class);
            event.setMessage("Some event for " + i++);
            app.logEvent(EventType.ERROR, event);
        }
        app.showCountCell();
        ac.close();
    }

    public void logEvent(EventType type, Event message) {
        EventLogger eventLogger;
        if (type == null) eventLogger = defaultLogger;
        else eventLogger = loggers.get(type);
        eventLogger.logEvent(message);
    }

    public StatisticsAspect getStatisticsAspect() {
        return statisticsAspect;
    }

    public void showCountCell() {
        getStatisticsAspect().getCounter()
                .forEach((aClass, integer) -> System.out.println(aClass.getSimpleName() + " - " + integer));
    }
}
