package assignment.client;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
public class BackupRepository {

    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public String getCount() {
        return entityManager.createQuery("select count(b) from BackupNumber b")
                .getSingleResult().toString();
    }

    @Transactional
    public void save(String[] data) {
        entityManager
                .createNativeQuery("INSERT INTO backup_number (id, number, stamp) values (?, ?, ?)")
                .setParameter(1, Long.parseLong(data[0]))
                .setParameter(2, Long.parseLong(data[1]))
                .setParameter(3, LocalDateTime.parse(data[2]))
                .executeUpdate();
    }

}
