package com.elias.desafioSOS.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDTO {

    private String fileName;

    private String fileDownload;

    private String fileType;

    private Long size;
}
