package com.in.demo.manage.manageit.data;

import com.in.demo.manage.manageit.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class TestsData {

    public static User generateSampleUser() {
        long userId = (long) (new Random().nextFloat() * 100);
        return new User("username" + userId, "password", new ArrayList<>(),
                new ArrayList<>(), true);
    }

    public static Project generateSampleProject() {
        User user = generateSampleUser();
        long projectId = (long) (new Random().nextFloat() * 100);
        return new Project(projectId, "sample project", "project description",
                user, new ArrayList<>());
    }

    public static Sprint generateSampleSprint() {
        long sprintId = (long) (new Random().nextFloat() * 100);
        return new Sprint(sprintId, "sample sprint",
                LocalDateTime.of(2021, 7, 10, 15, 30),
                LocalDateTime.of(2021, 7, 17, 15, 30),
                30, new ArrayList<>(), false, new ArrayList<>(), generateSampleProject());
    }

    public static Task generateSampleTask() {
        long taskId = (long) (new Random().nextFloat() * 100);
        return new Task(taskId, "sample task", "task description", 3,
                Progress.TO_DO, Priority.KINDA_IMPORTANT, generateSampleSprint());
    }
}
