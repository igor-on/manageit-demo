USE manage_it_db;

DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS sprints_users;
DROP TABLE IF EXISTS sprints;
DROP TABLE IF EXISTS tasks;

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(55) NOT NULL,
    password   VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS sprints
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                  VARCHAR(55) NOT NULL,
    start_date            DATETIME,
    end_date              DATETIME,
    story_points_to_spend INT         NULL,
    is_active             BOOLEAN DEFAULT false,
    user_id               BIGINT      NOT NULL,
    CONSTRAINT FK_UserSprint1 FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS tasks
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(55) NOT NULL,
    description  VARCHAR(255) NOT NULL,
    story_points INT,
    progress     ENUM ('TO_DO', 'IN_PROGRESS', 'DONE'),
    priority     ENUM ('1', '2', '3', '4', '5'),
    sprint_id    BIGINT      NOT NULL,
    CONSTRAINT FK_SprintTask FOREIGN KEY (sprint_id) REFERENCES sprints (id)
    );

CREATE TABLE IF NOT EXISTS projects
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(55) NOT NULL,
    description VARCHAR(255),
    owner_id    BIGINT      NOT NULL,
    CONSTRAINT FK_UserProject FOREIGN KEY (owner_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS sprints_users
(
    users_id   BIGINT,
    sprints_id BIGINT,
    CONSTRAINT FK_SprintUser FOREIGN KEY (sprints_id) REFERENCES sprints (id),
    CONSTRAINT FK_UserSprint FOREIGN KEY (users_id) REFERENCES users (id)
    );


