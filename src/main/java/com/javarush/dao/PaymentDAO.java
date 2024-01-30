package com.javarush.dao;

import com.javarush.entity.PaymentEntity;
import org.hibernate.SessionFactory;

public class PaymentDAO extends GenericDAO<PaymentEntity> {
    public PaymentDAO(SessionFactory sessionFactory) {
        super(PaymentEntity.class, sessionFactory);
    }
}
