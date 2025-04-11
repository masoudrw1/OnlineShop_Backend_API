package com.masoud.dto.site;

import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dto.files.FileDto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SliderDto {
    private Long id;
    private String title;
    private String link;
    private Integer orderNumber;
    private FileDto imag;
}
