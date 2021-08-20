package com.example.mongostudyadv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MongoStudyAdvApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoStudyAdvApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
        return args -> {
            Student student = Student.builder()
                    .firstName("Jamila")
                    .lastName("Ahmed")
                    .email("jahmed@gmail.com")
                    .gender(Gender.FEMALE)
                    .address(
                            Address.builder()
                                    .country("England")
                                    .city("London")
                                    .postCode("NE9")
                                    .build())
                    .favouriteSubjects(Arrays.asList("Computer Science", "Mathis"))
                    .totalSpentInBooks(BigDecimal.TEN)
                    .created(LocalDateTime.now())
                    .build();

//          useMongoTemplate(repository, mongoTemplate, student);

            if (repository
                    .findStudentByEmail(student.getEmail())
                    .isPresent()) {
                System.out.println(student + " already exists");
            } else {
                System.out.println("Inserting student " + student);
                repository.save(student);
            }
        };
    }

    private void useMongoTemplate(StudentRepository repository, MongoTemplate mongoTemplate, Student student) throws IllegalAccessException {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(student.getEmail()));

        List<Student> studentList = mongoTemplate.find(query, Student.class);

        if (studentList.size() > 1) {
            throw new IllegalAccessException("Found duplicated email " + student.getEmail());
        }

        if (studentList.isEmpty()) {
            System.out.println("Inserting student " + student);
            repository.save(student);
        } else {
            System.out.println(student + " already exists");
        }
    }
}
