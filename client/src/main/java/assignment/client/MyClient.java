package assignment.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@Slf4j
@RequiredArgsConstructor
@Component
public class MyClient {

    static final int PORT = 9090;
    private final String ip = "127.0.0.1";

    private final BackupRepository backupRepository;
    Socket client;
    BufferedReader input;
    PrintWriter output;

    public void request() {
        try {
            receive();
        } catch (Exception e) {
            log.info("==== 서버와의 통신 중단됨 ====");
            return;
        }
        log.info("==== 데이터 저장 완료 ====");
    }

    private void receive() throws IOException {
        log.info("===== 클라이언트 동작 =====");
        client = new Socket(ip, PORT);

        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));

        int offset = Integer.parseInt(backupRepository.getCount());
        output.println(offset);
        output.flush();

        while (true) {
            String[] data = input.readLine().split(" ");
            if (data.length < 3) {
                break;
            }
            log.info("received id={} number={} timestamp={}", data[0], data[1], data[2]);
            backupRepository.save(data);
            output.println("received");
            output.flush();
        }
    }

}
