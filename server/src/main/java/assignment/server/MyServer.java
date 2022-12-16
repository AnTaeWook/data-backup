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

    @Transactional
    public void serve() throws IOException {
        serverSocket = new ServerSocket(PORT);

        System.out.println("========================");
        System.out.println("===== 데이터 전송 서버 =====");
        System.out.println("========================");

        while (true) {
            child = serverSocket.accept();
            ServerThread serverThread = new ServerThread(child);
            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }
}

class ServerThread implements Runnable {

    Socket child;

    InputStream inputStream;
    ObjectInputStream objectInputStream;

    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;

    public ServerThread(Socket child) throws IOException {
        this.child = child;
        System.out.println("=== 클라이언트 연결됨 " + child.getInetAddress());

        inputStream = child.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);

        outputStream = child.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String data = (String) objectInputStream.readObject();
                System.out.println("데이터 수신 " + data);
                objectOutputStream.writeObject(data);
                objectOutputStream.flush();
            }
        } catch (Exception e) {
            System.out.println("클라이언트가 종료됨");
        } finally {

            try {
                inputStream.close();
                outputStream.close();
                objectInputStream.close();
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}