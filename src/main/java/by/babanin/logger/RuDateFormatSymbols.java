package by.babanin.logger;

import org.springframework.stereotype.Component;

import java.text.DateFormatSymbols;

@Component
public class RuDateFormatSymbols extends DateFormatSymbols {
    @Override
    public String[] getMonths() {
        return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
    }
}
