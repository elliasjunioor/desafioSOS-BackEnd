package com.elias.desafioSOS.service;

import com.elias.desafioSOS.domain.entity.Marca;
import com.elias.desafioSOS.domain.entity.Patrimonio;
import com.elias.desafioSOS.repositories.PatrimonioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PatrimonioService {

    private final PatrimonioRepository patrimonioRepository;


    public Patrimonio save(Patrimonio patrimonio, Marca marca){
        Random random = new Random();
        Integer numeroTombo = random.nextInt(9999);
        patrimonio.setMarca(marca);
        patrimonio.setNumeroTombo(numeroTombo.toString());
        return patrimonioRepository.save(patrimonio);
    }

    public List<Patrimonio> findAll(){
        return patrimonioRepository.findAll();
    }

    public Patrimonio findById(Long id){
        return patrimonioRepository.findById(id).orElse(null);
    }

    public Patrimonio update(Patrimonio patrimonio, Marca marca){
        Patrimonio patriomonioArquivo = findById(patrimonio.getId());
        patrimonio.setNameArquivo(patriomonioArquivo.getNameArquivo());
        Patrimonio patrimonioRetorno = findById(patrimonio.getId());
        patrimonio.setMarca(marca);
        patrimonio.setNumeroTombo(patrimonioRetorno.getNumeroTombo());
        return patrimonioRepository.save(patrimonio);
    }
    public Patrimonio updateArquivo(Patrimonio patrimonio){
        Patrimonio patrimonioRetorno = findById(patrimonio.getId());
        patrimonio.setNumeroTombo(patrimonioRetorno.getNumeroTombo());
        return patrimonioRepository.save(patrimonio);
    }

    public void delete(Long id){
        patrimonioRepository.delete(findById(id));
    }

    public List<Patrimonio> listAllPatrimonioMarca(Long id){
        return patrimonioRepository.findAllByMarcaId(id);
    }

}
