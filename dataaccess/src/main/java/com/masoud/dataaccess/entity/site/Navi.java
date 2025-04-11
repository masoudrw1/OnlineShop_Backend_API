package com.masoud.dataaccess.entity.site;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Navi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable=false)
    private String title;

    @Column(length = 1000,nullable=false)
    private String link;

    private Boolean enabled=true;
    private Integer orderNumber;




}
