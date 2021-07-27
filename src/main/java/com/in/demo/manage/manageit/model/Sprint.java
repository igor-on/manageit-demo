package com.in.demo.manage.manageit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sprints")
@AllArgsConstructor
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
    private Integer storyPointsToSpend;
    @JsonIgnoreProperties(value = {"sprint"})
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "sprint")
    private List<Task> tasks;
    private boolean isActive;

    public Sprint() {
        tasks = new ArrayList<>();
        isActive = false;
    }
}
