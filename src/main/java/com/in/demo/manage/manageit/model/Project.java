package com.in.demo.manage.manageit.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name can't be empty")
    @Size(min = 3, max = 55, message = "Project name has to be between 3 and 55 characters long")
    private String name;
    @Size(min = 3, max = 255, message = "Project description has to be between 3 and 255 characters long")
    private String description;
}
