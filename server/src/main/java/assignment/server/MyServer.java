package assignment.server;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MyServer {

    static final int PORT = 9090;

    private final EntityManager entityManager;
    ServerSocket serverSocket;
    Socket child;

    public void serve() throws IOException, InterruptedException {
        serverSocket = new ServerSocket(PORT);

        log.info("========================");
        log.info("===== 데이터 전송 서버 =====");
        log.info("========================");

        while (true) {
            child = serverSocket.accept();
            Thread thread = new ServerThread(child, entityManager);
            thread.start();
        }
    }
}

@Slf4j
class ServerThread extends Thread {

    private final int UNIT = 100;

    private final EntityManager entityManager;
    BufferedReader input;
    PrintWriter output;

    public ServerThread(Socket child, EntityManager entityManager) throws IOException, InterruptedException {
        this.entityManager = entityManager;
        log.info("=== 클라이언트 연결됨: {} ===", child.getInetAddress());
        sleep(1000);

        input = new BufferedReader(new InputStreamReader(child.getInputStream()));
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(child.getOutputStream())));
    }

    @Override
    @Transactional(readOnly = true)
    public void run() {
        try {
            send();
        } catch (NoResultException e) {
            output.println("transmission end");
            output.flush();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    private void send() throws IOException, InterruptedException {
        int offset = Integer.parseInt(input.readLine());
        while (true) {
            List<RandomNumber> resultList = getResultList(offset);
            if (resultList.isEmpty()) {
                throw new NoResultException("resource 서버에 데이터가 없습니다.");
            }

            for (RandomNumber data : resultList) {
                output.println(data.getId() + " " + data.getNumber() + " " + data.getStamp());
                output.flush();
                if (input.readLine() == null) {
                    throw new SocketException("클라이언트와의 통신이 중단되었습니다.");
                }
                sleep(1000);
            }
            offset += resultList.size();
        }
    }

    private List<RandomNumber> getResultList(int offset) {
        return entityManager
                .createQuery("select r from RandomNumber r order by r.stamp", RandomNumber.class)
                .setFirstResult(offset)
                .setMaxResults(UNIT)
                .getResultList();
    }
}