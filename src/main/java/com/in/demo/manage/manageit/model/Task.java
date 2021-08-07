package com.in.demo.manage.manageit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"sprint"})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Task name can't be empty")
    @Size(min = 3, max = 55, message = "Task name has to be between 3 and 55 characters long")
    private String name;
    @Size(min = 3, max = 255, message = "Task description has to be between 3 and 255 characters long")
    private String description;
    private Integer storyPoints;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('TO_DO', 'IN_PROGRESS', 'DONE')")
    private Progress progress;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('NOT_AT_ALL', 'KINDA_IMPORTANT', 'IMPORTANT', 'VERY_IMPORTANT', 'ASAP')")
    private Priority priority;
    @ManyToOne
    private Sprint sprint;

    // todo ---------- sprawdzic z service czy spoko
//    public void setSprint(Sprint sprint) {
//        this.sprint = sprint;
//        this.sprint.getTasks().add(this);
//        int pointsLeft = this.sprint.getStoryPointsToSpend();
//        this.sprint.setStoryPointsToSpend(pointsLeft - this.getStoryPoints());
//    }
}
