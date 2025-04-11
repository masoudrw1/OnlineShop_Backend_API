package com.masoud.dataaccess.entity.product;

import com.masoud.dataaccess.entity.files.Files;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000,nullable = false)
    private String title;

    @Column(columnDefinition = "text",nullable = false)
    private String description;

    private Boolean enabled=true;
    private Boolean exist=true;
    private Long price;
    private Long visitcount;
    private LocalDateTime addDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Files image;

    @ManyToMany
    @JoinTable(name = "product_Color",joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns =@JoinColumn(name = "color_id") )
    private Set<Color> colors;

    @ManyToMany
    @JoinTable(name = "product_size",joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "size_id"))
    private Set<Size> sizes;
    @ManyToOne
    private ProductCategory Category;
}
