package com.elias.desafioSOS.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatrimonioDTO {

    private Long id;

    private String nome;

    private String descricao;

    private Long idMarca;

}
