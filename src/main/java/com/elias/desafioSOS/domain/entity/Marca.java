package com.elias.desafioSOS.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MARCA")
public class Marca {

    @Id
    @GeneratedValue(generator = "MarcaSq")
    @SequenceGenerator(name = "MarcaSq", sequenceName = "ID_MARCA_SQ", allocationSize = 1)
    @Column(name = "ID_MARCA", nullable = false)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "NOME")
    private String nome;

    @Column(name = "NOME_ARQUIVO")
    private String nameArquivo;
}
