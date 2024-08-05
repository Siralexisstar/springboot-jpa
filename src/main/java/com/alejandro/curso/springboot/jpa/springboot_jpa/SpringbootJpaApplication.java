package com.alejandro.curso.springboot.jpa.springboot_jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alejandro.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.alejandro.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// List<Person> persons = (List<Person>) personRepository.findAll();
		// persons.stream()
		// .forEach(p -> System.out.println(p));
		// ;

		List<Person> persons2 = personRepository.findByProgrammingLanguage("Java");
		persons2.stream()
				.forEach(p -> System.out.println(p));

		System.out.println("*******************************************");

		List<Person> persons3 = personRepository.findByLastName("Garcia");
		persons3.stream()
				.forEach(p -> System.out.println(p));

		System.out.println("*******************************************");
		List<Person> persons4 = personRepository.encontrarPorLenguajeDeProgramacion("Java", "Alejandro");
		persons4.stream()
				.forEach(p -> System.out.println(p));
	}

}
