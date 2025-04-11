package com.masoud.dataaccess.entity.product;

import com.masoud.dataaccess.entity.files.Files;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000,nullable = false)
    private String title;

    @Column(columnDefinition = "text",nullable = false)
    private String description;

    private Boolean enabled=true;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Files image;
}
