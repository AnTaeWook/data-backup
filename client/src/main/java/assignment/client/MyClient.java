package assignment.client;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.Socket;

@RequiredArgsConstructor
@Repository
public class MyClient {

    private final EntityManager entityManager;

    Socket client;
    static final int PORT = 9090;
    String ip = "127.0.0.1";

    BufferedReader input;
    PrintWriter output;


    @Transactional
    public void request() throws IOException {
        try {
            System.out.println("===== 클라이언트 동작 =====");
            client = new Socket(ip, PORT);

            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));

            System.out.println("데이터 전송 시작");
            output.println("hello world!");
            output.flush();
        } catch (Exception e) {
            System.out.println("백업 완료");
        }
    }

}
