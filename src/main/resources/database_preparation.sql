USE manage_it_db;

DROP TABLE IF EXISTS sprints_users;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS sprints;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR(55)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled  BOOLEAN      NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

CREATE TABLE IF NOT EXISTS projects
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(55) NOT NULL,
    description    VARCHAR(255),
    owner_username VARCHAR(55) NOT NULL,
    CONSTRAINT FK_UserProject FOREIGN KEY (owner_username) REFERENCES users (username)
);

CREATE TABLE IF NOT EXISTS sprints
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                  VARCHAR(55) NOT NULL,
    start_date            DATETIME,
    end_date              DATETIME,
    story_points_to_spend INT,
    is_active             BOOLEAN DEFAULT false,
    project_id            BIGINT      NOT NULL,
    CONSTRAINT FK_ProjectSprint FOREIGN KEY (project_id) REFERENCES projects (id)
);

CREATE TABLE IF NOT EXISTS tasks
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(55)  NOT NULL,
    description  VARCHAR(255) NOT NULL,
    story_points INT,
    progress     ENUM ('TO_DO', 'IN_PROGRESS', 'DONE'),
    priority     ENUM ('NOT_AT_ALL', 'KINDA_IMPORTANT', 'IMPORTANT', 'VERY_IMPORTANT', 'ASAP'),
    sprint_id    BIGINT       NOT NULL,
    CONSTRAINT FK_SprintTask FOREIGN KEY (sprint_id) REFERENCES sprints (id)
);

CREATE TABLE IF NOT EXISTS sprints_users
(
    sprints_id     BIGINT,
    users_username VARCHAR(55),
    CONSTRAINT FK_SprintUser FOREIGN KEY (sprints_id) REFERENCES sprints (id),
    CONSTRAINT FK_UserSprint FOREIGN KEY (users_username) REFERENCES users (username)
);





