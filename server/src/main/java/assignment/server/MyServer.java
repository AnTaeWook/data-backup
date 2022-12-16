package assignment.server;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MyServer {

    private final EntityManager entityManager;

    ServerSocket serverSocket;
    static final int PORT = 9090;
    Socket child;

    public void serve() throws IOException, InterruptedException {
        serverSocket = new ServerSocket(PORT);

        System.out.println("========================");
        System.out.println("===== 데이터 전송 서버 =====");
        System.out.println("========================");

        while (true) {
            child = serverSocket.accept();
            Thread thread = new ServerThread(child, entityManager);
            thread.start();
        }
    }
}

class ServerThread extends Thread {

    private final EntityManager entityManager;

    Socket child;
    BufferedReader input;
    PrintWriter output;

    public ServerThread(Socket child, EntityManager entityManager) throws IOException, InterruptedException {
        this.child = child;
        this.entityManager = entityManager;
        System.out.println("=== 클라이언트 연결됨: " + child.getInetAddress() + " ===");
        sleep(1000);

        input = new BufferedReader(new InputStreamReader(child.getInputStream()));
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(child.getOutputStream())));
    }

    @Override
    @Transactional(readOnly = true)
    public void run() {
        try {
            int offset = Integer.parseInt(input.readLine());
            while (true) {
                List<RandomNumber> resultList = entityManager
                        .createQuery("select r from RandomNumber r order by r.stamp", RandomNumber.class)
                        .setFirstResult(offset)
                        .setMaxResults(100)
                        .getResultList();

                if (resultList.isEmpty()) {
                    throw new NoResultException("resource 서버에 데이터가 없습니다.");
                }

                for (RandomNumber data : resultList) {
                    output.println(data.getId() + " " + data.getNumber() + " " + data.getStamp());
                    output.flush();
                    if (input.readLine() == null) {
                        throw new SocketException();
                    }
                    sleep(1000);
                }
                offset += resultList.size();
            }
        } catch (NoResultException e) {
            output.println("transmission end");
            output.flush();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("==== 클라이언트와의 통신 중단됨 ====");
        }
    }
}