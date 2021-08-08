package com.in.demo.manage.manageit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@ToString(exclude = {"sprints"})
public class User {

    @Id
    @NotBlank(message = "Username can't be empty")
    @Size(min = 3, max = 55, message = "Username has to be between 3 and 55 characters long")
    private String username;
    @Column(nullable = false)
    @Size(min = 8, max = 155, message = "Password has to be at least 8 and max 155 characters long")
    private String password;
    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private List<Sprint> sprints;
    private boolean enabled;

    public User() {
        sprints = new ArrayList<>();
        enabled = true;
    }
}
