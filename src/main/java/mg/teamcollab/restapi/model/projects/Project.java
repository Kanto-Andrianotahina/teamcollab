package mg.teamcollab.restapi.model.projects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Project
{
    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter @Getter
    private String name;
    @Setter @Getter
    private String description;
    @Setter  @Getter
    private LocalDateTime createdAt;
    @Setter  @Getter
    private Long ownerId;

}
