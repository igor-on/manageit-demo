package com.in.demo.manage;

import com.in.demo.manage.manageit.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Random;

public class DbScript {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        Random random = new Random();

        Project sampleProject = new Project();
        User sampleUser = new User();
        Sprint sampleSprint = new Sprint();
        Task sampleTask = new Task();

        for (int i = 1; i < 5; i++) {
            sampleUser.setUsername("user" + i);
            sampleUser.setPassword("password" + i);
            sampleUser.setProjects(new ArrayList<>());
            sampleUser.setSprints(new ArrayList<>());
            ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8080/api/v1/users", sampleUser, User.class);
            System.out.println(response);
        }

        sampleUser = new User();
        sampleProject = new Project();
        sampleSprint = new Sprint();
        sampleTask = new Task();

        System.out.println("--------------------------------------------------");

        for (int i = 1; i < 7; i++) {

            sampleProject.setName("Project " + i);
            sampleProject.setDescription("Description " + i);
            sampleProject.setOwner(sampleUser);

            ResponseEntity<Project> response = restTemplate.postForEntity("http://localhost:8080/api/v1/projects", sampleProject, Project.class);

            System.out.println(response);
        }

        sampleUser = new User();
        sampleProject = new Project();
        sampleSprint = new Sprint();
        sampleTask = new Task();

        System.out.println("--------------------------------------------------");

        for (int i = 0; i < 10; i++) {
            sampleProject.setId(3L);

            sampleSprint.setName("Sprint " + i);
            sampleSprint.setStoryPointsToSpend(20 + i);
            sampleSprint.setUsers(new ArrayList<>());
            sampleSprint.setProject(sampleProject);

            ResponseEntity<Sprint> response = restTemplate.postForEntity("http://localhost:8080/api/v1/sprints", sampleSprint, Sprint.class);
            System.out.println(response);
        }

        sampleUser = new User();
        sampleProject = new Project();
        sampleSprint = new Sprint();
        sampleTask = new Task();

        System.out.println("--------------------------------------------------");

        for (int i = 1; i < 21; i++) {
            sampleSprint.setId((long) random.nextInt(10) + 1);

            sampleTask.setName("task " + i);
            sampleTask.setDescription("this is desc " + i);
            sampleTask.setStoryPoints(random.nextInt(8) + 1);
            sampleTask.setPriority(randomPriority(random.nextInt(5) + 1));
            sampleTask.setProgress(Progress.TO_DO);
            sampleTask.setSprint(sampleSprint);

//            ResponseEntity<Task> response = restTemplate.postForEntity("http://localhost:8080/api/v1/tasks", sampleTask, Task.class);
//
//            System.out.println(response);
        }

        System.out.println("--------------------------------------------------");

        sampleUser = new User();


    }


    public static Priority randomPriority(int number) {
        switch (number) {
            case 1:
                return Priority.ONE;
            case 2:
                return Priority.TWO;
            case 3:
                return Priority.THREE;
            case 4:
                return Priority.FOUR;
            case 5:
                return Priority.FIVE;
            default:
                return null;
        }
    }
}
