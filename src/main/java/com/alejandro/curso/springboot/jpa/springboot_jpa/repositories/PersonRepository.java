package com.alejandro.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.alejandro.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.alejandro.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    // Consulta como la de abajo pero con parametros y de forma dinamica
    @Query("select p from Person p where p.id in :ids")
    public List<Person> getPersonsByIds(@Param("ids") List<Long> ids);

    // Vamos a devolver un list de personas by id que coincidan en un rango
    @Query("select p from Person p where p.id in(1, 2, 3)")
    public List<Person> getPersonsByIds();

    // Consultamos el id de la persona que coincida con el id mas grande
    @Query("select p from Person p where p.id=(select max(p.id) from Person p)")
    public Optional<Person> getLastRegistration();

    // vamos a ver como anidar subconsultas dentro de la consulta principal
    @Query("select p.name, length(p.name) from Person p where length(p.name) = (select min(length(p.name)) from Person p)")
    public List<Object[]> getShorterName();

    @Query("select min(p.id), max(p.id), count(p.id), avg(length(p.name)), sum(p.id) from Person p where p.name is not null and p.id is not null")
    public Object getResumeAggregationFunction();

    // Vamos a calcular el nombre mas corto
    @Query("select min(length(p.name)) from Person p")
    public Integer getMinLengthName();

    // Vamos a calcular el nombre mas largo
    @Query("select max(length(p.name)) from Person p")
    public Integer getMaxLengthName();

    @Query("select p.name, length(p.name) from Person p")
    public List<Object[]> getPersonNameLength();

    // Consultamos el id maximo
    @Query("select max(p.id) from Person p")
    Long maxId();

    // Consultamos el id minimo
    @Query("select min(p.id) from Person p")
    Long minId();

    // Contamos los registros
    @Query("select count(p) from Person p")
    Long countTotalPerson();

    // Consulta usando JPA
    List<Person> findAllByOrderByNameAscLastNameDesc();

    // Buscamos todos los registros y los ordenamos por nombre asc y por apellido
    // desc
    @Query("select p from Person p order by p.name asc, p.lastName desc")
    List<Person> getAllOrderedList();

    // buscar entre id utilizando JPA , se podria hacer con cualquier campo
    List<Person> findByIdBetween(Long id1, Long id2);

    // buscar entre id utilizando JPA y ordenar ascendente
    List<Person> findByIdBetweenOrderByIdAsc(Long id1, Long id2);

    // Buscar entre id utilizando JPA y ordenar descendente
    List<Person> findByIdBetweenOrderByIdDesc(Long id1, Long id2);

    // Nuevo metodo between
    @Query("select p from Person p where p.id between 1 and 3")
    List<Person> findAllBetweenId();

    // Buscamos entre dos nombres y ordenamos por nombre
    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name")
    List<Person> findAllBetweenName(String name1, String name2);

    // con upper podemos convertir a mayuscula
    @Query("select upper(p.name) from Person p")
    List<String> findAllUpperName();

    // Buscamos todos los nombres y apellidos concatenados
    @Query("select concat(p.name, ' ', p.lastName) from Person p") // Concatenamos el nombre y el apellido
    List<String> findAllFullNameConcat();

    // Nombres y apellidos concatenados pero con doble pipe
    @Query("select p.name || ' ' || p.lastName from Person p")
    List<String> findAllFullNameConcatPipe();

    // Tambien podemos hacer un count
    @Query("select count(p) from Person p")
    long count();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllDistinctProgrammingLanguages();

    // Lista todos los nombres
    @Query("select p.name from Person p")
    List<String> findAllNames();

    // Lista todos los nombres pero con palabra reservada distinct
    @Query("select distinct(p.name) from Person p")
    List<String> findAllDistinctNames();

    @Query("select new com.alejandro.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastName) from Person p")
    List<PersonDto> findAllObjectPersonDto();

    @Query("select new Person(p.name, p.lastName) from Person p") /**
                                                                   * Es importante que el constructor exista sino
                                                                   * mierda
                                                                   */
    List<Person> findAllObjectPersonPersonalizedPerson();

    Optional<Person> findById(Long id);

    /** Con esto obtenemos el nombre por el id */
    @Query("SELECT p.name FROM Person p where p.id = ?1")
    String getNameById(Long id);

    @Query("select p.id from Person p where p.id = ?1")
    Long getIdById(Long id);

    @Query("select concat(p.name, ' ', p.lastName) from Person p where p.id = ?1") // Concatenamos el nombre y el
                                                                                   // apellido
    String getFullNameById(Long id);

    @Query("select p, p.programmingLanguage, p.name from Person p")
    List<Object[]> findAllMixPersonList();

    @Query("select p.id, p.name, p.lastName from Person p")
    List<Object[]> obtenerPersonaDataFullList(Long id);

    @Query("select p.id, p.name, p.lastName from Person p where p.id= ?1")
    Object[] obtenerPersonaDataFullById(Long id);

    @Query("SELECT p FROM Person p WHERE p.name = :name")
    Optional<Person> findOneName(@Param("name") String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<List<Person>> findOneLikeName(String name);

    // Lo mismo que la de arriba pero con otra nomentacion
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
