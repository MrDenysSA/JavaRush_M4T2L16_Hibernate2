package com.javarush.dao;

import com.javarush.entity.RentalEntity;
import org.hibernate.SessionFactory;

public class RentalDAO extends GenericDAO<RentalEntity> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(RentalEntity.class, sessionFactory);
    }
}
