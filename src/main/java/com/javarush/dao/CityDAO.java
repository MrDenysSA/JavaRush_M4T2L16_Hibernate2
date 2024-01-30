package com.javarush.dao;

import com.javarush.entity.CityEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDAO extends GenericDAO<CityEntity> {
    public CityDAO(SessionFactory sessionFactory) {
        super(CityEntity.class, sessionFactory);
    }

    public CityEntity getByName(String name) {
        Query<CityEntity> query = getCurrentSession()
                .createQuery("select c from CityEntity c where c.city = :NAME", CityEntity.class);
        query.setParameter("NAME", name);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
