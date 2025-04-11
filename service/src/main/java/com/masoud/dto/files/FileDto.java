package com.masoud.dto.files;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String name;
    private String uuid;
    private Long size;
    private String extension;
    private String path;
    private String contentType;
}
