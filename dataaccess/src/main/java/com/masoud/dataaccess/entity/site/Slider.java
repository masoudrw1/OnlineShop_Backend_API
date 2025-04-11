package com.masoud.dataaccess.entity.site;

import com.masoud.dataaccess.entity.files.Files;
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

public class Slider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String link;

    private Boolean enabled=true;
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Files imag;

}
