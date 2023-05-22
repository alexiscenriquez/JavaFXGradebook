CREATE DATABASE gradebook;
    USE gradebook;
CREATE TABLE `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ;
CREATE TABLE `class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `num` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
);

CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `student_class` (
  `class_id` int NOT NULL,
  `student_id` int NOT NULL,
  `grade` double DEFAULT NULL,
  KEY `class_id` (`class_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `student_class_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`),
  CONSTRAINT `student_class_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ;
INSERT INTO `teacher` (`first_name`, `last_name`, `username`, `password`) VALUES
('John', 'Doe', 'johndoe', 'password1'),
('Jane', 'Smith', 'janesmith', 'password2'),
('Michael', 'Johnson', 'michaeljohnson', 'password3'),
('Emily', 'Brown', 'emilybrown', 'password4'),
('David', 'Davis', 'daviddavis', 'password5'),
('Sarah', 'Miller', 'sarahmiller', 'password6'),
('Christopher', 'Wilson', 'christopherwilson', 'password7'),
('Jennifer', 'Taylor', 'jennifertaylor', 'password8'),
('Daniel', 'Anderson', 'danielanderson', 'password9'),
('Amy', 'Thomas', 'amythomas', 'password10');

INSERT INTO `class` (`teacher_id`, `name`, `num`) VALUES
(1, 'Mathematics', 101),
(1, 'Science', 102),
(2, 'English', 201),
(2, 'History', 202),
(3, 'Physics', 301),
(4, 'Chemistry', 302),
(5, 'Biology', 401),
(6, 'Geography', 402),
(7, 'Computer Science', 501),
(8, 'Art', 502);

INSERT INTO `student` (`first_name`, `last_name`, `username`, `password`) VALUES
('Emma', 'Johnson', 'emmajohnson', 'password11'),
('Noah', 'Smith', 'noahsmith', 'password12'),
('Olivia', 'Davis', 'oliviadavis', 'password13'),
('Liam', 'Brown', 'liambrown', 'password14'),
('Sophia', 'Miller', 'sophiamiller', 'password15'),
('Mason', 'Wilson', 'masonwilson', 'password16'),
('Ava', 'Taylor', 'avataylor', 'password17'),
('Ethan', 'Anderson', 'ethananderson', 'password18'),
('Isabella', 'Thomas', 'isabellathomas', 'password19'),
('William', 'Clark', 'williamclark', 'password20');

INSERT INTO `student_class` (`class_id`, `student_id`, `grade`) VALUES
(1, 1, 95.5),
(1, 2, 88.0),
(2, 3, 92.3),
(2, 4, 78.9),
(3, 5, 87.2),
(3, 6, 91.8),
(4, 7, 95.0),
(4, 8, 82.6),
(5, 9, 88.7),
(5, 10, 76.4);

