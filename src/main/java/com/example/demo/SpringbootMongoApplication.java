package com.example.demo;

import com.example.demo.persistence.Address;
import com.example.demo.persistence.Gender;
import com.example.demo.persistence.Student;
import com.example.demo.persistence.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringbootMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate template) {
		return  args -> {
			Address address = new Address("England", "23", "London");
			String email = "johndoe@gmail.com";
			Student student = new Student(
					"John",
					"Doe",
					email,
					Gender.MALE,
					address,
					List.of("math","history"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

		//	usingMongoTemplateAndQuery(repository, template, email, student);

			repository.findStudentByEmail(email)
					.ifPresentOrElse(s -> {
						System.out.println(s + " already exists");
					}, ()->{
						System.out.println("Inserting student " + student);
						repository.insert(student);
					});
		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate template, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = template.find(query, Student.class);
		if(students.size() > 1) {
			throw new IllegalStateException("Found many students with email " + email);
		}

		if(students.isEmpty()) {
			repository.insert(student);
		} else {
			System.out.println(student + " already exists");
		}
	}
}
