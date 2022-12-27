package com.example.nosqldemo;

import com.example.nosqldemo.entity.Address;
import com.example.nosqldemo.entity.Gender;
import com.example.nosqldemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.example.nosqldemo.repository.StudentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class NosqlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NosqlDemoApplication.class, args);

    }

    @Bean
    CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){
        return args -> {
            String email = "bshop@gmail.com";
            Address address = new Address("USA ", "22016", "Man");
          Student student = new Student("Bob", "Bishop", email, Gender.MALE,
                  address, List.of("Geography", "Computer Sience", "BAP"), BigDecimal.TEN, LocalDateTime.now());

          repository.findStudentByEmail(email)
                  .ifPresentOrElse(s -> {
              System.out.println(s.getEmail() + " :Student already exists");
          }, ()-> {
              System.out.println("Inserting student");
              repository.insert(student);
          });

            //usingMongoTemplateQuery(com.example.nosqldemo.repository, mongoTemplate, email, student);
        };
    }

    private void usingMongoTemplateQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        List<Student> students = mongoTemplate.find(query, Student.class);

        if (students.size() > 1){
            throw new IllegalStateException("found more than 1 students with email: " + email);
        }

        if (students.isEmpty()){
            System.out.println("Successful inserted");
            repository.insert(student);
        } else {
            System.out.println("student already exists");
        }
    }

}
