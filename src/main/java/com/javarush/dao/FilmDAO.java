package com.javarush.dao;

import com.javarush.entity.FilmEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends GenericDAO<FilmEntity> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(FilmEntity.class, sessionFactory);
    }

    public FilmEntity getFirstAvailableFilmForRent() {
        Query<FilmEntity> query = getCurrentSession()
                .createQuery("select f from FilmEntity f where f.id not in (select distinct film.id from InventoryEntity )", FilmEntity.class);
        query.setMaxResults(1);
        return query.getSingleResult();

    }
}
