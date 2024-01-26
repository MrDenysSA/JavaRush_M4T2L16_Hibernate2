package com.javarush.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(schema = "movie", name = "film_text")
public class FilmTextEntity {

    @Id
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @OneToOne
    @JoinColumn(name = "film_id")
    private  FilmEntity film;

    private String title;

    @Column(columnDefinition = "text")
    @Type(type = "text")
    private String description;
}
