package com.masoud.dto.site;

import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dataaccess.enums.BlogStatus;
import com.masoud.dto.files.FileDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {

    private Long id;
    private String title;
    private String subtitle;
    private LocalDateTime publishDate;
    private BlogStatus status;
    private Long visitcount;
    private FileDto imag;
    private String description;

}
