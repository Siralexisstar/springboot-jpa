package com.alejandro.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alejandro.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    // Vamos a poner los metodos que necesitamos
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    List<Person> findByLastName(String lastName);

    /*Podemos hacer una consulta personalizada. Ejemplo */
    @Query("select p from Persons p where p.programmingLanguage = 1? and p.lastName = 2?")
    List<Person> encontrarPorLenguajeDeProgramacion(String programmingLanguage, String lastName);

}
