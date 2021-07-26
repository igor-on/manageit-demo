package com.in.demo.manage.manageit.data;

import com.in.demo.manage.manageit.model.*;

import java.time.LocalDateTime;
import java.util.*;

public class TestsData {

    public static Project generateSampleProject() {
        long projectId = (long) (new Random().nextFloat() * 100);
        return new Project(projectId, "sample project", "project description");
    }

    public static Sprint generateSampleSprint() {
        long sprintId = (long) (new Random().nextFloat() * 100);
        return new Sprint(sprintId, "sample sprint",
                LocalDateTime.of(2021, 7, 10, 15, 30),
                LocalDateTime.of(2021, 7, 17, 15, 30),
                30, new ArrayList<>(), true);
    }

    public static Task generateSampleTask() {
        long taskId = (long) (new Random().nextFloat() * 100);
        return new Task(taskId, "sample task", "task description", 3,
                Progress.TO_DO, Priority.TWO, generateSampleSprint());
    }
}