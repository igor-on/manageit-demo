USE manage_it_db;

CREATE TABLE IF NOT EXISTS projects(
id BIGINT AUTO_INCREMENT,
name VARCHAR(55) NOT NULL,
description VARCHAR(255),
CONSTRAINT PK_project PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sprints(
id BIGINT AUTO_INCREMENT,
name VARCHAR(55) NOT NULL,
start_date DATETIME NOT NULL,
end_date DATETIME NOT NULL,
CONSTRAINT PK_sprint PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tasks(
id BIGINT AUTO_INCREMENT,
name VARCHAR(55) NOT NULL,
description VARCHAR(255),
story_points INT,
progress ENUM('TO_DO', 'IN_PROGRESS', 'DONE'),
weight ENUM('1', '2', '3', '4', '5'),
sprint_id BIGINT NOT NULL,
CONSTRAINT PK_task PRIMARY KEY (id),
CONSTRAINT FK_SprintTask FOREIGN KEY (sprint_id) REFERENCES sprints(id)
);


ALTER TABLE sprints ADD COLUMN story_points_to_spend INT NULL AFTER end_date;
ALTER TABLE tasks CHANGE COLUMN weight progress ENUM('1', '2', '3', '4', '5');

SELECT * FROM projects;
SELECT * FROM sprints;
