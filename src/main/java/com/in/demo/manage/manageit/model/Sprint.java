package com.in.demo.manage.manageit.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sprints")
@Setter
@Getter
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name can't be empty")
    @Size(min = 3, max = 55, message = "Project name has to be between 3 and 55 characters long")
    private String name;
    @PastOrPresent
    private LocalDateTime startDate;
    @Future
    private LocalDateTime endDate;
    @OneToMany(mappedBy = "sprint")
    private Set<Task> tasks;


    public Sprint() {
        startDate = new Jsr310JpaConverters.LocalDateTimeConverter().convertToEntityAttribute(Date.from(Instant.now()));
        tasks = new HashSet<>();
    }
}
