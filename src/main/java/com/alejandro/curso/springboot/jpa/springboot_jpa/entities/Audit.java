package com.alejandro.curso.springboot.jpa.springboot_jpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Embeddable
@Data
public class Audit {

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Esto es para antes de que se cargue algo en la bbdd
    @PrePersist
    public void prePersist() {
        System.out.println("evento del  ciclo de vida del entity pre-persist");
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void PreUpdate() {
        System.out.println("evento del  ciclo de vida del entity pre-update");
        this.updatedAt = LocalDateTime.now();
    }

}
