package com.alejandro.curso.springboot.jpa.springboot_jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons") // Ponemos el certificado del nombre de la tabla
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    // Vamos a definir los atributos de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "programming_language") // ponemos el nombre correcta de la columna
    private String programmingLanguage;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                '}';
    }

    /** Creamos un constructor personalizado */
    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
}
