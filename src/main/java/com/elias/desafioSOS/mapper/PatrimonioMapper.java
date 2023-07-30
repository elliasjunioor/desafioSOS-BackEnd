package com.elias.desafioSOS.mapper;

import com.elias.desafioSOS.domain.dto.PatrimonioDTO;
import com.elias.desafioSOS.domain.dto.PatrimonioRetornoDTO;
import com.elias.desafioSOS.domain.entity.Patrimonio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatrimonioMapper {

    PatrimonioMapper INSTANCE = Mappers.getMapper(PatrimonioMapper.class);

    PatrimonioDTO entityToPatrimonioDTO (Patrimonio patrimonio);

    Patrimonio dtoToEntity (PatrimonioDTO patrimonioDTO);

    PatrimonioRetornoDTO entityToPatrimonioRetornoDTO (Patrimonio patrimonio);
}
