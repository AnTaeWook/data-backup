package assignment.resource;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class App {

    private final DataGenerator dataGenerator;

    // 한 트랜잭션 당 insert 될 데이터 개수
    private final int DataCountPerTransaction = 10;

    // 총 트랜잭션 횟수 (총 데아터 횟수 = DataCountPerTransaction * totalTransactionCount)
    private final int totalTransactionCount = 10;

    @PostConstruct
    public void init() throws InterruptedException {
        long primaryKey = 1L;
        for (int i = 0; i < totalTransactionCount; i++) {
            dataGenerator.generateUnit(primaryKey, DataCountPerTransaction);
            primaryKey += DataCountPerTransaction;
        }
    }

}
