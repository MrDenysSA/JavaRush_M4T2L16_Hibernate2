package com.javarush.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "country", schema = "movie", catalog = "")
public class CountryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "country_id")
    private Object countryId;

    public Object getCountryId() {
        return countryId;
    }

    public void setCountryId(Object countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "country")
    private String country;


    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;


}
