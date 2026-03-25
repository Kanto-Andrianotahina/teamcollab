package mg.teamcollab.restapi.model.comments;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.teamcollab.restapi.model.tasks.Task;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(columnDefinition = "TEXT")
    @Getter @Setter
    private String content;

    @CreationTimestamp
    @Column(updatable = false)
    @Getter @Setter
    private LocalDateTime createdAt;

    @Getter @Setter
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @Getter @Setter
    private Task task;
}