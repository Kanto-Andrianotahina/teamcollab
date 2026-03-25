package mg.teamcollab.restapi.model.projectmembers;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mg.teamcollab.restapi.model.projectmembesrole.ProjectMemberRole;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.model.users.User;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "projectmember",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "project_id"})
        }
)
public class ProjectMember {

    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Setter  @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private ProjectMemberRole role;

    @Setter @Getter
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    public void prePersist() {
        this.joinedAt = LocalDateTime.now();
    }



}