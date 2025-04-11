package com.masoud.dto.site;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NaviDto {
    private Long id;
    private String title;
    private String link;
    private Integer orderNumber;
}
