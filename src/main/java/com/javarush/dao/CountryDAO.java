package com.javarush.dao;

import com.javarush.entity.CountryEntity;
import org.hibernate.SessionFactory;

public class CountryDAO extends GenericDAO<CountryEntity> {
    public CountryDAO(SessionFactory sessionFactory) {
        super(CountryEntity.class, sessionFactory);
    }
}
