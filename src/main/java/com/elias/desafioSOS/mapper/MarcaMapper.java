package com.elias.desafioSOS.mapper;

import com.elias.desafioSOS.domain.dto.MarcaDTO;
import com.elias.desafioSOS.domain.entity.Marca;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MarcaMapper {
    MarcaMapper INSTANCE = Mappers.getMapper(MarcaMapper.class);

    MarcaDTO entityToMarcaDto(Marca marca);

    Marca dtoToMarcaEntity(MarcaDTO marcaDTO);
}
