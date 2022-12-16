package assignment.resource;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class App {

    private final DataGenerator dataGenerator;
    private final int UNIT = 10;

    @PostConstruct
    public void init() throws InterruptedException {
        long primaryKey = 1L;
        for (int i = 0; i < 10; i++) {
            dataGenerator.generateUnit(primaryKey, UNIT);
            primaryKey += UNIT;
        }
    }

}
