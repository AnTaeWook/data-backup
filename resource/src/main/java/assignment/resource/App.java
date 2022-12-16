package assignment.resource;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class App {

    private final DataGenerator dataGenerator;

    @PostConstruct
    public void init() throws InterruptedException {
        dataGenerator.generate();
    }

}
