package com.javarush.dao;

import com.javarush.entity.FilmTextEntity;
import org.hibernate.SessionFactory;

public class FilmTextDAO extends GenericDAO<FilmTextEntity> {
    public FilmTextDAO(SessionFactory sessionFactory) {
        super(FilmTextEntity.class, sessionFactory);
    }
}
