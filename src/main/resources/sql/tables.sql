use ms_test;

BEGIN;

CREATE TABLE IF NOT EXISTS USERS (
	ID VARCHAR(255) PRIMARY KEY NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    PHONE VARCHAR(255),
    EMAIL VARCHAR(255) UNIQUE,
    PWD VARCHAR(255) NOT NULL,
    NICKNAME VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS POSTS (
      ID INT AUTO_INCREMENT PRIMARY KEY,
      USERID VARCHAR(255) NOT NULL,
      TITLE VARCHAR(255) NOT NULL,
      NICKNAME VARCHAR(255),
      TEXTCONTENT TEXT,
      CATEGORY VARCHAR(255) NOT NULL,
      REFERENCEDID INT,
      CREATEDAT DATETIME,
      UPDATEDAT DATETIME,
      FOREIGN KEY (USERID) REFERENCES USERS(ID)
);

CREATE TABLE IF NOT EXISTS COMMENTS_STATUS (
    STATUS VARCHAR(20) PRIMARY KEY NOT NULL
);

CREATE TABLE IF NOT EXISTS COMMENTS (
    ID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    POST_ID INT NOT NULL,
    USER_ID VARCHAR(255) NOT NULL,
    PARENT_ID INT,
    TEXT VARCHAR(255) NOT NULL,
    STATUS VARCHAR(20) DEFAULT "active" NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,

    FOREIGN KEY (POST_ID) REFERENCES POSTS(ID),
    FOREIGN KEY (PARENT_ID) REFERENCES COMMENTS(ID) ON DELETE CASCADE,
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
    FOREIGN KEY (STATUS) REFERENCES COMMENTS_STATUS(STATUS)
);



COMMIT;