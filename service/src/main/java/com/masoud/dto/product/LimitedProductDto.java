package com.masoud.dto.product;

import com.masoud.dto.files.FileDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitedProductDto {
    private Long id;
    private String title;
    private Long price;
    private Long visitcount;
    private LocalDateTime addDate;
    private FileDto image;
    private Set<ColorDto> colors;
    private Set<SizeDto> sizes;
    private ProductCategoryDto Category;
}
