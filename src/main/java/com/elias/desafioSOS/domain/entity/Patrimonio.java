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
@Table(name = "PATRIMONIO")
public class Patrimonio {

    @Id
    @GeneratedValue(generator = "PatrimonioSq")
    @SequenceGenerator(name = "PatrimonioSq", sequenceName = "ID_PATRIMONIO_SQ", allocationSize = 1)
    @Column(name = "ID_PATRIMONIO", nullable = false)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "NOME")
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "NUMERO_TOMBO")
    private String numeroTombo;

    @Column(name = "NOME_ARQUIVO")
    private String nameArquivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MARCA", referencedColumnName = "ID_MARCA")
    private Marca marca;

}
