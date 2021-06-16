package com.example.demo;

import com.example.demo.persistence.Address;
import com.example.demo.persistence.Gender;
import com.example.demo.persistence.Student;
import com.example.demo.persistence.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	CommandLineRunner runner(StudentRepository repository) {
		return  args -> {
			Address address = new Address("England", "23", "London");
			Student student = new Student(
					"John",
					"Doe",
					"johndoe@gmail.com",
					Gender.MALE,
					address,
					List.of("math","history"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);
			repository.insert(student);
		};
	}
}
