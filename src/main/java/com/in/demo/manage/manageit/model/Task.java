package com.in.demo.manage.manageit.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
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
