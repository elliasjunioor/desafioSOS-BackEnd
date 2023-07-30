package com.elias.desafioSOS.controller;

import com.elias.desafioSOS.domain.dto.MarcaDTO;
import com.elias.desafioSOS.domain.dto.PatrimonioRetornoDTO;
import com.elias.desafioSOS.domain.dto.UploadFileDTO;
import com.elias.desafioSOS.mapper.MarcaMapper;
import com.elias.desafioSOS.mapper.PatrimonioMapper;
import com.elias.desafioSOS.service.FileStorageMarca;
import com.elias.desafioSOS.service.MarcaService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "01-Marca", description = "Controller responsável pela MARCA")
@RestController
@RequiredArgsConstructor
@EnableJpaRepositories
@RequestMapping("/marca")
@Controller
public class MarcaController {

    private final MarcaService marcaService;

    private final FileStorageMarca fileStorageMarca;


    @Operation(summary = "Salvar Marca")
    @PostMapping
    public ResponseEntity<MarcaDTO> save(@RequestBody MarcaDTO marcaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(MarcaMapper.INSTANCE.entityToMarcaDto(marcaService.save(MarcaMapper.INSTANCE.dtoToMarcaEntity(marcaDTO))));
    }

    @Operation(summary = "Obter Marca por Id")
    @GetMapping
    public ResponseEntity<MarcaDTO> getMarca(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(MarcaMapper.INSTANCE.entityToMarcaDto(marcaService.findById(id)));
    }

    @Operation(summary = "Obter Lista de Marcas")
    @GetMapping("/list-all")
    public ResponseEntity<List<MarcaDTO>> getAllMarca(){
        List<MarcaDTO> listMarca = marcaService.findAll()
                .stream()
                .map(MarcaMapper.INSTANCE::entityToMarcaDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listMarca);
    }

    @Operation(summary = "Atualizar Marca")
    @PutMapping
    public ResponseEntity<MarcaDTO> update(@RequestBody MarcaDTO marcaDTO){
        return ResponseEntity.status(HttpStatus.OK).body(MarcaMapper.INSTANCE.entityToMarcaDto(marcaService.save(MarcaMapper.INSTANCE.dtoToMarcaEntity(marcaDTO))));
    }

    @Operation(summary = "Excluindo uma marca")
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id){
        marcaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Listar Patrimonios")
    @GetMapping("list-all-patrimonio")
    public ResponseEntity<List<PatrimonioRetornoDTO>> getAllPatrimonioByMarca(Long id){
        List<PatrimonioRetornoDTO> listPatrimonio = marcaService.listPatrimonio(id)
                .stream()
                .map(PatrimonioMapper.INSTANCE::entityToPatrimonioRetornoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listPatrimonio);
    }

    @PostMapping("/uploadFile")
    public UploadFileDTO uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long id) {
        var fileName = fileStorageMarca.storeFile(file, id);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/marca/downloadFile/")
                .path(fileName).
                toUriString();
        return new UploadFileDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/")
    public ResponseEntity<Resource> dowloadFile(@RequestParam String filename, HttpServletRequest request, @RequestParam Long id) {
        Resource resource = fileStorageMarca.loadFile(filename);
        String contentType = "";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {

        }

        if (contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + marcaService.findById(id).getNameArquivo() + "\")")
                .body(resource);
    }



}
