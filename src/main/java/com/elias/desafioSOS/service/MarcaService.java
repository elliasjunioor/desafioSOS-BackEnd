package com.elias.desafioSOS.service;

import com.elias.desafioSOS.domain.entity.Marca;
import com.elias.desafioSOS.domain.entity.Patrimonio;
import com.elias.desafioSOS.exceptions.MarcaExistException;
import com.elias.desafioSOS.repositories.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;

    private final PatrimonioService patrimonioService;



    @Transactional
    public Marca save(Marca marca){
        if (!ObjectUtils.isEmpty(marcaRepository.findByNome(marca.getNome()))){
                throw new MarcaExistException();
        }
         return marcaRepository.save(marca);
    }
    public Marca findById(Long id){
        return marcaRepository.findById(id).orElse(null);
    }
    public List<Marca> findAll(){
        return marcaRepository.findAll();
    }

    public void delete(Long id){
       marcaRepository.delete(findById(id));
    }

    public List<Patrimonio> listPatrimonio(Long id){
        return patrimonioService.listAllPatrimonioMarca(id);
    }

    public Marca update(Marca marca){
        return marcaRepository.save(marca);
    }



}
