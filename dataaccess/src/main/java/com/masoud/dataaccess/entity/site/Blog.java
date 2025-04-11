package com.masoud.dataaccess.entity.site;

import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dataaccess.enums.BlogStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable=false)
    private String title;

    @Column(length = 1000,nullable=false)
    private String subtitle;

    @Column(columnDefinition = "text")
    private String description;

    private LocalDateTime publishDate;
    private BlogStatus status;
    private Long visitcount;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Files imag;








}
