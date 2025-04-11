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
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000,nullable = false)
    private String name;

    @Column(length = 100,nullable = false)
    private String hex;

}
