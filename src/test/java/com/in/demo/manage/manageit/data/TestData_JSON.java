package com.in.demo.manage.manageit.data;

public class TestData_JSON {

    // todo -> zastanowic sie nad najlepszym i najbardziej optymalnym rozwiazaniem walidacji
    //                                                                (czy z @Valid czy bez)
    // todo -> walidacja znosna, mozna pomyslec nad ustawieniem dodatkowej warstwy (~~ validator service)
    //                                                           do walidacji i mapowania z encji na dto
    //                                                          (~~ fasada - walidacja, serwis mapowanie)
    // todo - > find a better way to add tasks (?) (manageit.model.Sprint)
    // todo -> from globalErrorHandler => wraca czy zostaje to co teraz ?
    //                              return new Error(constraintViolation.get().getMessage(),
    //                                   LocalDateTime.now(Clock.systemUTC()), HttpStatus.BAD_REQUEST.value());


    // todo ->              givenSprintId_ShouldReturnSprint() -> zamiast taskIds zwraca cale taski
    //                      givenTaskId_ShouldReturnTask() -> NPE w mapperze
    //                      testAddNewSprint_ShouldThrowException_WhenIdIsNotNull() -> nie wykrzacza (?)
    //                      assertNotNull(actual.getId()) (wykrzacza (?why) -> Sprint, Project), po usunieciu - git
    //                      testAddNewTask_WhenSuccess i WhenTaskIsNotNull (na ta chwile NPE w TaskService sie
    //                                                              pojawia przy przypisywaniu Taska do Sprintu)
    //                      czemu nie moglyby byc zmienne 'final' w dto?
    //                      problem n + 1 zapytañ


    public static String sampleTasksList_JSON = "[{" +
            "\"id\": 2L," +
            "\"name\": \"sprintTask_two\"," +
            "\"description\": \"desc2\"," +
            "\"storyPoints\": 4," +
            "\"progress\": \"IN_PROGRESS\"," +
            "\"priority\": 3," +
            "\"sprintId\": 100L" +
            "}," +
            "{" +
            "\"id\": 3L," +
            "\"name\": \"sprintTask_three\"," +
            "\"description\": \"desc3\"," +
            "\"storyPoints\": 5," +
            "\"progress\": \"DONE\"," +
            "\"priority\": 4," +
            "\"sprintId\": 100L" +
            "}]";

    public static String sampleTask_JSON = "{" +
            "\"id\": 1L," +
            "\"name\": \"sprintTask_one\"," +
            "\"description\": \"desc1\"," +
            "\"storyPoints\": 3," +
            "\"progress\": \"TO_DO\"," +
            "\"priority\": 2," +
            "\"sprintId\": 1L" +
            "}";

    public static String anotherSampleTask_JSON = "{" +
            "\"id\": 100L," +
            "\"name\": \"sprintTask_hundred\"," +
            "\"description\": \"desc100\"," +
            "\"storyPoints\": 3," +
            "\"progress\": \"DONE\"," +
            "\"priority\": 4," +
            "\"sprintId\": 100L" +
            "}";

    public static String sampleSprint_JSON = "{" +
            "\"id\": 1L," +
            "\"name\": \"sprint1\"," +
            "\"startDate\": \"10 July 2021, 3:30 PM\"," +
            "\"endDate\": \"17 July 2021, 3:30 PM\"," +
            "\"storyPointsToSpend\": \"30\"," +
            "\"taskIds\": " + sampleTasksList_JSON +
            "}";

    public static String anotherSampleSprint_JSON = "{" +
            "\"id\": 100L," +
            "\"name\": \"sprint1\"," +
            "\"startDate\": \"17 July 2021, 3:30 PM\"," +
            "\"endDate\": \"24 July 2021, 3:30 PM\"," +
            "\"storyPointsToSpend\": \"30\"," +
            "\"taskIds\": " + sampleTasksList_JSON +
            "}";

    public static String sampleProject_JSON = "{" +
            "\"id\": 1L," +
            "\"name\": \"project: 1\"," +
            "\"description\": \"sample project with id: 1\"," +
            "}";

    public static String anotherSampleProject_JSON = "{" +
            "\"id\": 100L," +
            "\"name\": \"project: 100\"," +
            "\"description\": \"sample project with id: 100\"," +
            "}";
}
