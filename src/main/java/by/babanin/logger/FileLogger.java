package by.babanin.logger;

import by.babanin.model.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class FileLogger implements EventLogger {
    private String filename;

    public FileLogger(@Value("${filename}") String filename) {
        this.filename = filename;
    }

    @PostConstruct
    private void init() throws IOException {
        File file = new File(filename);
        file.createNewFile();
        boolean isWrite = file.canWrite();
        if (!isWrite) throw new IOException("Файл зпрещен для записи");
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(
                    new File(filename),
                    event.toString() + "\n",
                    Charset.forName("UTF-8"),
                    true
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
