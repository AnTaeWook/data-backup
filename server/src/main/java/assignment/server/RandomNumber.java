package assignment.server;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class RandomNumber {

    @Id
    private Long id;

    private Long number;

    private LocalDateTime stamp;
}
