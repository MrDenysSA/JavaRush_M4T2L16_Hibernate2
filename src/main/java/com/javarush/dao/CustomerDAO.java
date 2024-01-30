package com.javarush.dao;

import com.javarush.entity.CustomerEntity;
import org.hibernate.SessionFactory;

public class CustomerDAO extends GenericDAO<CustomerEntity> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(CustomerEntity.class, sessionFactory);
    }
}
