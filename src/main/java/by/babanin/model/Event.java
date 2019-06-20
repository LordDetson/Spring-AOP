package by.babanin.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringJoiner;

public class Event {
    private final static Random RANDOM = new Random();
    private int id;
    private String message;
    private Date date;
    private DateFormat format;

    public Event(Date date, SimpleDateFormat format) {
        this.date = date;
        this.format = format;
        id = RANDOM.nextInt();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("message='" + message + "'")
                .add("date=" + format.format(date))
                .toString();
    }
}
