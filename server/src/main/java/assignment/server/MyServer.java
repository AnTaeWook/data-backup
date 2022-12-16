package assignment.server;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@RequiredArgsConstructor
@Repository
public class MyServer {

    private final EntityManager entityManager;

    ServerSocket serverSocket;
    static final int PORT = 9090;
    Socket child;

    @Transactional(readOnly = true)
    public void serve() throws IOException {
        serverSocket = new ServerSocket(PORT);

        System.out.println("========================");
        System.out.println("===== 데이터 전송 서버 =====");
        System.out.println("========================");

        while (true) {
            child = serverSocket.accept();
            Thread thread = new ServerThread(child);
            thread.start();
        }
    }
}

class ServerThread extends Thread {

    Socket child;
    BufferedReader input;
    PrintWriter output;

    public ServerThread(Socket child) throws IOException {
        this.child = child;
        System.out.println("=== 클라이언트 연결됨 " + child.getInetAddress() + " ===");

        input = new BufferedReader(new InputStreamReader(child.getInputStream()));
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(child.getOutputStream())));
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("데이터 수신 대기");
                String data = input.readLine();
                System.out.println("데이터 수신 " + data);
            }
        } catch (Exception e) {
            System.out.println("클라이언트가 종료됨");
        }
    }
}