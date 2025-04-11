package com.masoud.dto.site;

import com.masoud.dataaccess.enums.BlogStatus;
import com.masoud.dto.files.FileDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimetedBlogDto {

    private Long id;
    private String title;
    private String subtitle;
    private LocalDateTime publishDate;
    private Long visitcount;
    private FileDto imag;

}
