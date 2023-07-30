package com.elias.desafioSOS.service;

import com.elias.desafioSOS.domain.entity.Marca;
import com.elias.desafioSOS.exceptions.FileStorageExcepetion;
import com.elias.desafioSOS.exceptions.MyFileNotFoundExcepcion;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileStorageMarca {

    private Path fileStorageLocation;

    private final Environment environment;

    private final MarcaService marcaService;


    @Transactional
    public String storeFile(MultipartFile file, Long id) {
        Marca marca = marcaService.findById(id);
        Path path = Paths.get(environment.getProperty("app.file.upload-dir", ".data/uploads/marca"))
                .toAbsolutePath().normalize();
        fileStorageLocation = path;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageExcepetion("Não foi possivel criar o diretorio!", e);
        }
        marca.setNameArquivo(file.getOriginalFilename());
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            marcaService.update(marca);
            return filename;
        } catch (Exception e) {
            throw new FileStorageExcepetion("Não foi possível salvar o arquivo " + filename, e);
        }
    }

    private Path pathFile(String filename) {
        Path path = Paths.get(environment.getProperty("app.file.upload-dir", ".data/uploads/marca"))
                .resolve(filename).normalize();
        return path;
    }

    public Resource loadFile(String filename) {
        try {
            Path filePath = pathFile(filename);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else throw new MyFileNotFoundExcepcion("Arquivo não encontrado");
        } catch (Exception e) {
            throw new MyFileNotFoundExcepcion("Arquivo não encontrado" + filename, e);
        }
    }

    public void deleteFile(String filename) {
        try {
            Path filePath = pathFile(filename);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new MyFileNotFoundExcepcion("Arquivo não encontrado" + filename, e);
        }
    }

}
