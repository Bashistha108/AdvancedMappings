
use spring_boot_advanced_mappings_final;

create table professor_details(
	id int NOT NULL auto_increment,
    role varchar(80),
    password varchar(50),
    primary key (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE professors(
	id int NOT NULL auto_increment,
    first_name varchar(45),
    last_name varchar(45),
    email varchar(90) NOT NULL UNIQUE,
    professor_details_id int,
    enabled TINYINT(1) DEFAULT 1,
	PRIMARY KEY(`id`),
    FOREIGN KEY (`professor_details_id`) REFERENCES `professor_details`(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE courses(
	id int NOT NULL PRIMARY KEY auto_increment,
    title varchar(50) UNIQUE,
    professor_id int ,
    FOREIGN KEY (professor_id) references professors(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




CREATE TABLE reviews(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    comment varchar(256) DEFAULT NULL,
    course_id int DEFAULT NULL,
    FOREIGN KEY(course_id) REFERENCES courses(id)
);


CREATE TABLE students(
	id int primary key auto_increment,
    first_name varchar(50) DEFAULT NULL,
    last_name varchar(50) DEFAULT NULL,
    email varchar(100) UNIQUE NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



CREATE TABLE course_student(
	course_id int NOT NULL,
    student_id int NOT NULL,
    PRIMARY KEY(course_id, student_id),
    FOREIGN KEY(student_id) REFERENCES students(id),
    FOREIGN KEY(course_id) REFERENCES courses(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;










