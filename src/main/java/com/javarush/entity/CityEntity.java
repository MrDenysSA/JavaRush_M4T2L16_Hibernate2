package com.javarush.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
@Data
@Entity
@Table(name = "city", schema = "movie")
public class CityEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "city_id")
    private Short id;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity countryId;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;
}
