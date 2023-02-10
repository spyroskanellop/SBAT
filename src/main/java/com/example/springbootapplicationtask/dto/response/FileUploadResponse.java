package com.example.springbootapplicationtask.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {

    private String filename;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
