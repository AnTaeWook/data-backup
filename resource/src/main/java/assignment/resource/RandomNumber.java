package assignment.resource;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class RandomNumber {

    @Id
    private Long id;

    private Long number;

    private LocalDateTime timestamp;
}
