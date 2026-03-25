package mg.teamcollab.restapi.model.tasks;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
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
    private LocalDate dueDate;
    @Column(nullable = false, unique = true)
    @Getter @Setter
    private Long assignedUser;

    public Task(String title, String status, Long userId) {
        this.setTitle(title);
        this.setStatus(status);
        this.setAssignedUser(userId);
    }
}
