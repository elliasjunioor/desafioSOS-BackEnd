package com.elias.desafioSOS.controller;

import com.elias.desafioSOS.domain.dto.MarcaDTO;
import com.elias.desafioSOS.domain.dto.PatrimonioDTO;
import com.elias.desafioSOS.domain.dto.PatrimonioRetornoDTO;
import com.elias.desafioSOS.domain.dto.UploadFileDTO;
import com.elias.desafioSOS.mapper.MarcaMapper;
import com.elias.desafioSOS.mapper.PatrimonioMapper;
import com.elias.desafioSOS.service.FileStoragePatrimonio;
import com.elias.desafioSOS.service.PatrimonioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "02. Patrimonio", description = "Controller responsavel pelo Patrimonio")
@RestController
@RequiredArgsConstructor
@EnableJpaRepositories
@RequestMapping("/patrimonio")
public class PatrimonioController {

    private final PatrimonioService patrimonioService;

    private final FileStoragePatrimonio fileStoragePatrimonio;

    @Operation(summary = "Salvar Patrimonios")
    @PostMapping
    public ResponseEntity<PatrimonioRetornoDTO> save(@RequestBody PatrimonioDTO patrimonioDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(PatrimonioMapper.INSTANCE.entityToPatrimonioRetornoDTO(patrimonioService
                .save(PatrimonioMapper.INSTANCE.dtoToEntity(patrimonioDTO))));
    }

    @Operation(summary = "Atualizar Patrimonios")
    @PutMapping
    public ResponseEntity<PatrimonioRetornoDTO> update(@RequestBody PatrimonioDTO patrimonioDTO){
        return ResponseEntity.status(HttpStatus.OK).body(PatrimonioMapper.INSTANCE.entityToPatrimonioRetornoDTO(patrimonioService
                .update(PatrimonioMapper.INSTANCE.dtoToEntity(patrimonioDTO))));
    }
    @Operation(summary = "Obter Patrimonio por ID")
    @GetMapping
    public ResponseEntity<PatrimonioRetornoDTO> getPatrimonio(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(PatrimonioMapper.INSTANCE.entityToPatrimonioRetornoDTO(patrimonioService.findById(id)));
    }

    @Operation(summary = "Listar Patrimonios")
    @GetMapping("list-all")
    public ResponseEntity<List<PatrimonioRetornoDTO>> getAllPatrimonio(){
        List<PatrimonioRetornoDTO> listPatrimonio = patrimonioService.findAll()
                .stream()
                .map(PatrimonioMapper.INSTANCE::entityToPatrimonioRetornoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listPatrimonio);
    }

    @Operation(summary = "Excluindo um Patrimonio")
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id){
        patrimonioService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/uploadFile")
    public UploadFileDTO uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long id) {
        var fileName = fileStoragePatrimonio.storeFile(file, id);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/marca/downloadFile/")
                .path(fileName).
                toUriString();
        return new UploadFileDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/")
    public ResponseEntity<Resource> dowloadFile(@RequestParam String filename, HttpServletRequest request, @RequestParam Long id) {
        Resource resource = fileStoragePatrimonio.loadFile(filename);
        String contentType = "";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {

        }

        if (contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + patrimonioService.findById(id).getNameArquivo() + "\")")
                .body(resource);
    }

}
