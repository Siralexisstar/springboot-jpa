package com.alejandro.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.alejandro.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findById(Long id);

    @Query("SELECT p FROM Person p WHERE p.name = :name")
    Optional<Person> findOneName(@Param("name") String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<List<Person>> findOneLikeName(String name);

    //Lo mismo que la de arriba pero con otra nomentacion
    Optional<List<Person>> findByNameContaining(String name);

    // Vamos a poner los metodos que necesitamos
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    List<Person> findByLastName(String lastName);

    /* Podemos hacer una consulta personalizada. Ejemplo */
    @Query("select p from Person p where p.programmingLanguage = ?1 and p.lastName = ?2")
    List<Person> encontrarPorLenguajeDeProgramacion(String programmingLanguage, String lastName);

    /* La consulta de arriba con Jpa directa seria */
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    /* Vamos a obtener todos los datos de una Persona */
    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerDatosDeLaPersona();

    /*
     * Podemos tener incluso sobrecarga de metodos
     * -Este ultimo tiene una condicion doble where, and
     */
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage = ?1 and p.name = ?2")
    List<Object[]> obtenerDatosDeLaPersona(String programmingLanguage, String name);

    /**
     * Podemos hacer otro metodo sobrecargado
     */
    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> obtenerDatosDeLaPersona(String name);

    /**
     * Otro tipo de query mas
     */
    @Query("select p.programmingLanguage from Person p where p.programmingLanguage = ?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

}
