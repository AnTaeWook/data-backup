package assignment.client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class BackupNumber {

    @Id
    private Long id;

    private Long number;

    private LocalDateTime stamp;
}
