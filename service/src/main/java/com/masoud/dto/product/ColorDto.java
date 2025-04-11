package com.masoud.dto.product;

import com.masoud.dataaccess.entity.product.Color;
import com.masoud.dataaccess.entity.product.Size;
import com.masoud.dto.files.FileDto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorDto {
    private Long id;
    private String name;
    private String hex;
}
