package com.in.demo.manage.manageit.model;


import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sprints")
@NoArgsConstructor
@Setter
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @OneToMany(mappedBy = "sprint")
    private Set<Task> tasks;
}
