package com.masoud.dto.site;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {
    private Long id;
    private String keyname;
    private String valueCuntent;

}
