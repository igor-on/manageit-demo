package com.in.demo.manage.manageit.service.data;

import com.in.demo.manage.manageit.model.Project;

import java.util.Random;

public class DataForServicesTests {

    public static Project generateSampleProject() {
        Random random = new Random();
        long randomId = random.nextLong();
        return new Project(randomId, "project" + randomId, "this is sample project with id" + randomId);
    }
}
