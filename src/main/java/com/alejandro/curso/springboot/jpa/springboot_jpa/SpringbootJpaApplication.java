package com.alejandro.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;

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

		findOne();
	}

	public void findOne() {
		// Person person = null;
		// Optional<Person> optionalPerson = personRepository.findById(1L);
		// if (!optionalPerson.isEmpty()) {
		// 	person = optionalPerson.get();
		// }

		// System.out.println(person);
		personRepository.findByNameContaining("an").ifPresent(System.out::println);
	}

	public void list() {

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

		List<Person> persons4 = personRepository.encontrarPorLenguajeDeProgramacion("Java", "Garcia");
		persons4.stream()
				.forEach(p -> System.out.println(p));

		System.out.println("*******************************************");

		List<Person> persons5 = personRepository.findByProgrammingLanguageAndName("Java", "Alejandro");
		persons5.stream()
				.forEach(p -> System.out.println(p));

		System.out.println("*******************************************");

		List<Object[]> personsObjectValue = personRepository.obtenerDatosDeLaPersona();
		personsObjectValue.stream()
				/* Esto me dara el nombre p[0] y el lenguaje p[1] */
				.forEach(p -> {
					System.out.println(p[0] + " es experto en el lenguaje: " + p[1]);
				});

		System.out.println("*******************************************");

		List<Object[]> personsObjectValue2 = personRepository.obtenerDatosDeLaPersona("Java", "Alejandro");

		personsObjectValue2.stream()
				/* Esto me dara el nombre p[0] y el lenguaje p[1] */
				.forEach(p -> {
					System.out.println(p[0] + " es experto en el lenguaje: " + p[1]);
				});

	}

}
