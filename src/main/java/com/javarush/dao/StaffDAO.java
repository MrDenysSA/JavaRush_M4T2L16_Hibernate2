package com.javarush.dao;

import com.javarush.entity.StaffEntity;
import org.hibernate.SessionFactory;

public class StaffDAO extends GenericDAO<StaffEntity> {
    public StaffDAO(SessionFactory sessionFactory) {
        super(StaffEntity.class, sessionFactory);
    }
}
