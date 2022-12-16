package assignment.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class App {

    private final MyClient myClient;

    @PostConstruct
    public void init() throws IOException {
        myClient.request();
    }
}
