package com.javarush.dao;

import com.javarush.entity.RentalEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class RentalDAO extends GenericDAO<RentalEntity> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(RentalEntity.class, sessionFactory);
    }

    public RentalEntity getAnyUnreturnedRental() {
        Query<RentalEntity> query = getCurrentSession()
                .createQuery("select r from RentalEntity r where r.returnDate is null ", RentalEntity.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
