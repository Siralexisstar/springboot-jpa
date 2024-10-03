package com.alejandro.curso.springboot.jpa.springboot_jpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
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

		// create();
		update();
		// delete2();
		// list();
		// findOne();
		// personalizedQueries();
		// personalizedQueries2();
		// personalizedQueriesDistinct();
		// personalizedQueriesConcatUpperAndLowerCase();
		// personalizedQueriesWithBetween();
		// queriesFunctionAggregation();
		// subqueries();

	}

	@Transactional(readOnly = true)
	public void subqueries() {

		System.out.println("===== consulta por el nombre mas corto y su largo ========");

		List<Object[]> shorterName = personRepository.getShorterName();
		shorterName.stream().forEach(sN -> {
			System.out.println("Nombre: " + sN[0]);
			System.out.println("Largo: " + sN[1]);
		});

		System.out.println("===== consulta por el ultimo registro que se inserto ========");
		Optional<Person> lastRegistration = personRepository.getLastRegistration();
		lastRegistration.ifPresentOrElse(System.out::println, () -> System.out.println("No existen registros"));

		System.out.println("===== consulta de los registros en una lista ========");
		List<Person> personsByIds = personRepository.getPersonsByIds();
		personsByIds.stream().forEach(System.out::println);

		System.out.println("===== consulta de los registros en una lista pero pasandolos por parametros ========");
		personRepository.getPersonsByIds(Arrays.asList(1L, 2L, 3L))
				.stream()
				.forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void queriesFunctionAggregation() {

		System.out.println("===== consulta con el nombre mas corto de la tabla persona =====");
		Integer minName = personRepository.getMinLengthName();
		System.out.println(minName);

		System.out.println("===== consulta con el nombre mas largo de la tabla persona =====");
		Integer maxName = personRepository.getMaxLengthName();
		System.out.println(maxName);

		System.out.println("===== consulta con el total de registros de la tabla persona =====");
		Long count = personRepository.countTotalPerson();
		System.out.println(count);

		System.out.println("===== consulta con el valor minimo del id de la tabla persona =====");
		Long min = personRepository.minId();
		System.out.println(min);

		System.out.println("===== consulta con el valor maximo del id de la tabla persona =====");
		Long max = personRepository.maxId();
		System.out.println(max);

		System.out.println(" consulta con el nombre y su largo ");
		List<Object[]> nameLength = personRepository.getPersonNameLength();
		nameLength.stream().forEach(nL -> {
			System.out.println("Nombre " + nL[0]);
			System.out.println("Largo " + nL[1]);
		});

		System.out.println("===== consultas resumen de funciones de agregacion min, max, sum, avg, count =====");
		Object[] resumeObjects = (Object[]) personRepository.getResumeAggregationFunction();
		System.out.println("min: " + resumeObjects[0]);
		System.out.println("max: " + resumeObjects[1]);
		System.out.println("count: " + resumeObjects[2]);
		System.out.println("avg: " + resumeObjects[3]);
		System.out.println("sum: " + resumeObjects[4]);
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesWithBetween() {

		System.out.println("consulta para recuperar los id en un rango");
		List<Person> persons = personRepository.findAllBetweenId();
		persons.stream().forEach(System.out::println);

		System.out.println("Otra manera de hacer esto seria la siguiente");
		List<Person> persons2 = personRepository.findAllBetweenName("Ana", "Paco");
		persons2.stream().forEach(System.out::println);

		System.out.println("Utilizando consultas JPA seria");
		List<Person> persons3 = personRepository.findByIdBetween(1L, 3L);
		persons3.stream().forEach(System.out::println);

		System.out.println("Vamos a utilizar las otras consultas JPA");
		List<Person> persons4 = personRepository.findByIdBetweenOrderByIdAsc(1L, 3L);
		persons4.stream().forEach(System.out::println);

		System.out.println("Vamos a ordenar los registros por id descendente entre 1 y 3");
		List<Person> persons5 = personRepository.findByIdBetweenOrderByIdDesc(1L, 3L);
		persons5.stream().forEach(System.out::println);

		System.out.println("Vamos a buscar entre dos nombres y ordenar por nombre descendente");
		List<Person> personsAll = personRepository.getAllOrderedList();
		personsAll.stream().forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase() {

		System.out.println(" ======= consulta de nombres con los apellidos concatenados ========");
		List<String> names = personRepository.findAllFullNameConcat();
		names.forEach(System.out::println);

		System.out.println("Otra manera seria:");
		List<String> names2 = personRepository.findAllFullNameConcatPipe();
		names2.forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct() {

		System.out.println("=========== consultas con nombres de personas ===========");
		List<String> names = personRepository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("=========== consulta de los nombres de las personas diferentes ===========");
		List<String> namesDistinct = personRepository.findAllDistinctNames();
		namesDistinct.forEach(System.out::println);

		System.out.println("=========== consulta lenguajes de programacion distintos ============");
		List<String> programmingLanguagesDistinct = personRepository.findAllDistinctProgrammingLanguages();
		programmingLanguagesDistinct.forEach(System.out::println);

		System.out.println("=========== Retorna la cuenta de todos los regiuros ===========");
		Long count = personRepository.count();
		System.out.println(count);

	}

	/**
	 * Realiza consultas personalizadas a la base de datos. En este caso, se
	 * consulta solo el nombre por el id de la persona, el id, el nombre completo y
	 * los lenguajes de programacion. Tambien se muestra como obtener la
	 * informacion de la persona en una sola consulta, y como obtener los datos de
	 * la persona en una sola consulta, pero con los campos en un array de
	 * objetos.
	 */
	@Transactional(readOnly = true)
	public void personalizedQueries() {

		Scanner sc = new Scanner(System.in);

		System.out.println(" ============ consulta solo el nombre por el id ============ ");
		System.out.println("Introduce el id de la persona");
		Long id = sc.nextLong();

		String name = personRepository.getNameById(id);
		System.out.println(name);
		Long idPersona = personRepository.getIdById(id);
		System.out.println(idPersona);

		String fullName = personRepository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println(" =================================== ");
		List<Object[]> obtenerPersonaDataFullList = personRepository.obtenerPersonaDataFullList(id);
		obtenerPersonaDataFullList.stream().findFirst().ifPresent(d -> {
			System.out.println("nombre" + d[0]);
			System.out.println("lenguaje" + d[1]);
			System.out.println("apellidos" + d[2]);
		});

		System.out.println(" =================================== ");
		Object[] obtenerPersonaDataFullById = personRepository.obtenerPersonaDataFullById(id);
		System.out.println("id=" + obtenerPersonaDataFullById[0]);
		System.out.println("nombre=" + obtenerPersonaDataFullById[1]);
		System.out.println("lenguaje=" + obtenerPersonaDataFullById[2]);
		System.out.println("apellidos=" + obtenerPersonaDataFullById[3]);

	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {

		Scanner sc = new Scanner(System.in);

		System.out.println(" ============ consulta solo el nombre por el id ============ ");
		System.out.println("Introduce el id de la persona");
		Long id = sc.nextLong();

		List<Object[]> allMixPersonList = personRepository.findAllMixPersonList();

		System.out.println("Primera forma de imprimir los registros");
		allMixPersonList.forEach(d -> System.out.println(d.toString()));

		System.out.println("================================");

		System.out.println("Segunda forma de imprimir los registros");
		allMixPersonList.stream().forEach(d -> {

			System.out.println("nombre=" + d[0]);
			System.out.println("lenguaje=" + d[1]);
		});

		System.out.println("COnsulta que puebla y devuelve un objeto entity de una instancia personalizada");
		List<Person> persons = personRepository.findAllObjectPersonPersonalizedPerson();
		persons.forEach(p -> System.out.println(p));

		System.out.println("consulta que puebla y devuelve un objeto dto de una clase personalizada");
		List<PersonDto> persons2 = personRepository.findAllObjectPersonDto();
		persons2.forEach(p -> System.out.println(p));

	}

	@Transactional
	public void delete2() {
		personRepository.findAll().forEach(System.out::println);

		/** Vamos a ver como borramos un registro de la bbdd */
		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce el id de la persona a borrar");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = personRepository.findById(id);

		// Aqui lo que hacemos es decirle si esta presente eliminas (utilizando method
		// reference)
		// y si no esta presente imprimes un mensaje por consola.
		optionalPerson.ifPresentOrElse(personRepository::delete, () -> System.out.println("No existe la persona"));

		personRepository.findAll().forEach(System.out::println);
		sc.close();
	}

	@Transactional
	public void delete() {
		/** Vamos a ver como borramos un registro de la bbdd */
		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce el id de la persona a borrar");
		Long id = sc.nextLong();

		// Esta es la forma directa de borrar
		// personRepository.deleteById(id);

		personRepository.findById(id).ifPresent(p -> {

			personRepository.delete(p);
		});

		// Mostramos todos los registros de la bbdd
		personRepository.findAll().forEach(p -> System.out.println(p));

		sc.close();
	}

	@Transactional
	public void update() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce el id de la persona a actualizar");
		Long id = sc.nextLong();
		System.out.println("Introduce el nuevo nombre de la persona");
		String name = sc.next();
		System.out.println("Introduce el nuevo apellido de la persona");
		String lastName = sc.next();
		System.out.println("Introduce el nuevo lenguaje de programacion de la persona");
		String programmingLanguage = sc.next();

		// tenemos que recuperar la persona por el id de la bbdd
		personRepository.findById(id).map(p -> {
			p.setName(name);
			p.setLastName(lastName);
			p.setProgrammingLanguage(programmingLanguage);
			return personRepository.save(p);
		}).orElseThrow(() -> {

			throw new RuntimeException("No se ha encontrado la persona con el id " + id);
		});

		/** Otra forma de acerlo pero con COnsumer */
		// personRepository.findById(id).ifPresent(p -> {

		// 	p.setName(name);
		// 	p.setLastName(lastName);
		// 	p.setProgrammingLanguage(programmingLanguage);
		// 	personRepository.save(p);
		// });

		sc.close();

	}

	@Transactional
	public void create() {

		// Vamos a introducir el nuevo regristo manualmente en bbdd
		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce el nombre de la persona");
		String name = sc.next();
		System.out.println("Introduce el apellido de la persona");
		String lastName = sc.next();
		System.out.println("Introduce el lenguaje de programacion de la persona");
		String programmingLanguage = sc.next();

		sc.close();

		// Dejamos vacio el id por que es autogenerado
		Person person = new Person(null, name, lastName, programmingLanguage);

		// Con esto podemos guardar la persona en la bbdd
		Person personNew = personRepository.save(person);

		System.out.println(personNew);

	}

	@Transactional(readOnly = true)
	public void findOne() {
		// Person person = null;
		// Optional<Person> optionalPerson = personRepository.findById(1L);
		// if (!optionalPerson.isEmpty()) {
		// person = optionalPerson.get();
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
