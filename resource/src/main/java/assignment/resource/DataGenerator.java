package assignment.resource;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Repository
@RequiredArgsConstructor
public class DataGenerator {

    private final EntityManager entityManager;

    @Transactional
    public void generateUnit(long primaryKey, int dataCountPerTransaction) throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < dataCountPerTransaction; i++) {
            entityManager.createNativeQuery("INSERT INTO random_number (id, number, stamp) values (?, ?, ?)")
                    .setParameter(1, primaryKey++)
                    .setParameter(2, random.nextLong())
                    .setParameter(3, LocalDateTime.now())
                    .executeUpdate();
            Thread.sleep(100);
        }
    }

}

