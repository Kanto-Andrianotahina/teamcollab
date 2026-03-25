package mg.teamcollab.restapi.mapper.tasks;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String title;
    @Column(nullable = false)
    private String status;
    @CreationTimestamp
    private LocalDate dueDate;
    @Column(nullable = false, unique = true)
    private Long assignedUser;
}
