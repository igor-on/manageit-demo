package com.in.demo.manage.manageit.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name can't be empty")
    @Size(min = 3, max = 55, message = "Project name has to be between 3 and 55 characters long")
    private String name;
    @Size(min = 3, max = 255, message = "Project description has to be between 3 and 255 characters long")
    private String description;
    private Integer storyPoints;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('TO_DO', 'IN_PROGRESS', 'DONE')")
    private Progress progress;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('1', '2', '3', '4', '5')")
    private Weight weight;
    @ManyToOne
    private Sprint sprint;
}