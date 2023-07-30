package com.elias.desafioSOS.repositories;

import com.elias.desafioSOS.domain.entity.Patrimonio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {

    List<Patrimonio> findAllByMarcaId(Long id);
}
