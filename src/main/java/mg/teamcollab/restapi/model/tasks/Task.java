package mg.teamcollab.restapi.model.tasks;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Column(nullable = false,columnDefinition = "TEXT")
    @Getter @Setter
    private String title;
    @Column(nullable = false)
    @Getter @Setter
    private String status;
    @CreationTimestamp
    @Getter @Setter
    private LocalDateTime dueDate;
    @Getter @Setter
    private Long assignedUser;
    @Getter @Setter
    private Long projectId;
    public Task(String title, String status, Long userId) {
        this.setTitle(title);
        this.setStatus(status);
        this.setAssignedUser(userId);
    }
}
