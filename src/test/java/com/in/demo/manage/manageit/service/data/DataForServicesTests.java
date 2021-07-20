package com.in.demo.manage.manageit.service.data;

import com.in.demo.manage.manageit.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class DataForServicesTests {

    public static Project generateSampleProject() {
        Random random = new Random();
        long randomId = random.nextLong();
        return new Project(randomId, "project" + randomId, "this is sample project with id" + randomId);
    }

    public static Sprint returnSampleSprint() {
        return new Sprint(1L, "sprint1",
                LocalDateTime.of(2021, 7, 10, 15, 30),
                LocalDateTime.of(2021, 7, 17, 15, 30),
                30, new ArrayList<>());
    }

    public static Task returnSampleTask() {
        return new Task(2L, "task1", "desc1", 4,
                Progress.TO_DO, Priority.TWO, returnSampleSprint());
    }
}