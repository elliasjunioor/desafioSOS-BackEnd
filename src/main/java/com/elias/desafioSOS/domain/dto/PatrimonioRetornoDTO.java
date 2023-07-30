package com.elias.desafioSOS.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatrimonioRetornoDTO {

    private Long id;

    private String nome;

    private String descricao;

    private String numeroTombo;

    private MarcaDTO marca;
}
