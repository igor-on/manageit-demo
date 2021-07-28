package com.in.demo.manage.manageit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"projects", "sprints"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Username can't be empty")
    @Size(min = 3, max = 55, message = "Username has to be between 3 and 55 characters long")
    private String username;
    @Column(nullable = false)
    @Size(min = 8, max = 55, message = "Password has to be at least 8 and max 55 characters long")
    private String password;
    @JsonIgnoreProperties(value = {"projects"})
    @OneToMany(mappedBy = "owner")
    private List<Project> projects;
    @JsonIgnoreProperties(value = {"sprints"})
    @ManyToMany(mappedBy = "users")
    private List<Sprint> sprints;
}