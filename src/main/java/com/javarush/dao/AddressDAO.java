package com.javarush.dao;

import com.javarush.entity.AddressEntity;
import org.hibernate.SessionFactory;

public class AddressDAO extends GenericDAO<AddressEntity> {
    public AddressDAO(SessionFactory sessionFactory) {
        super(AddressEntity.class, sessionFactory);
    }
}
