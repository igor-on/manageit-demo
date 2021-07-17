package com.in.demo.manage.manageit.model;

import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Data
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
    private Progress progress; // todo ------- może TaskState? (tylko trzeba by to edytować tez w tableach w DB)
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('1', '2', '3', '4', '5')")
    private Weight weight;  // todo ------- może TaskWeight? (tylko trzeba by to edytować tez w tableach w DB)
    @ManyToOne
    private Sprint sprint;
}
