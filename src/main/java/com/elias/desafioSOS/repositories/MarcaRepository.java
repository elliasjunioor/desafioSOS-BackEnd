package com.elias.desafioSOS.repositories;

import com.elias.desafioSOS.domain.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    Marca findByNome(String nome);
}
