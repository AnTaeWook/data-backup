package assignment.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@RequiredArgsConstructor
@Component
public class MyClient {

    private final BackupRepository backupRepository;

    Socket client;
    static final int PORT = 9090;
    String ip = "127.0.0.1";

    BufferedReader input;
    PrintWriter output;

    public void request() throws IOException {
        try {
            System.out.println("===== 클라이언트 동작 =====");
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
                System.out.println(data[0] + " " + data[1] + " " + data[2]);
                backupRepository.save(data);
                output.println("received");
                output.flush();
            }

        } catch (Exception e) {
            System.out.println("==== 서버와의 통신 중단됨 ====");
            return;
        }
        System.out.println("==== 데이터 저장 완료 ====");
    }

}
