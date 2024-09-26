package com.alejandro.curso.springboot.jpa.springboot_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private String name;
    private String lastName;

    @Override
    public String toString() {
        return "PersonDto [name=" + name + ", lastName=" + lastName + "]";
    }

}

