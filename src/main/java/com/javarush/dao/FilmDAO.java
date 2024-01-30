package com.javarush.dao;

import com.javarush.entity.FilmEntity;
import org.hibernate.SessionFactory;

public class FilmDAO extends GenericDAO<FilmEntity> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(FilmEntity.class, sessionFactory);
    }
}
