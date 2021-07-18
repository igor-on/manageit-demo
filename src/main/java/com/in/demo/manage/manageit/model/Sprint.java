package com.in.demo.manage.manageit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "sprints")
@Data
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Sprint must have a name")
    @Size(min = 3, max = 55, message = "Sprint name has to be between 3 and 55 characters long")
    private String name;
    private LocalDateTime startDate;
    @Future
    private LocalDateTime endDate;
    //TODO poprawic impl dodawania taskow
    private Integer storyPointsToSpend;
    @JsonIgnoreProperties(value = {"sprint"})
    @OneToMany(mappedBy = "sprint")
    private List<Task> tasks;

    public Sprint() {
        tasks = new ArrayList<>();
    }
}
