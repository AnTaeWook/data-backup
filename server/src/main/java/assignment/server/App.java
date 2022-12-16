package assignment.server;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class App {

    private final MyServer myServer;

    @PostConstruct
    public void init() throws IOException {
        myServer.serve();
    }

}
