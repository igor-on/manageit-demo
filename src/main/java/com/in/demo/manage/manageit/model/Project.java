package com.in.demo.manage.manageit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name can't be empty")
    @Size(min = 3, max = 55, message = "Project name has to be between 3 and 55 characters long")
    private String name;
    @Size(min = 3, max = 255, message = "Project description has to be between 3 and 255 characters long")
    private String description;
    @ManyToOne
    @JoinColumn(name = "owner_username")
    private User owner;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "project")
    private List<Sprint> sprints = new ArrayList<>();

    // todo ---------- sprawdzic z service czy spoko
//    public void setOwner(User owner) {
//        this.owner = owner;
//        this.owner.getProjects().add(this);
//    }
}
