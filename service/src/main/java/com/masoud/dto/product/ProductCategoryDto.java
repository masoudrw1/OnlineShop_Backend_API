package com.masoud.dto.product;

import com.masoud.dto.files.FileDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {

    private Long id;
    private String title;
    private String description;
    private FileDto image;
}
