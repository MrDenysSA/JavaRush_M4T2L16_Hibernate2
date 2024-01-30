package com.javarush.dao;

import com.javarush.entity.LanguageEntity;
import org.hibernate.SessionFactory;

public class LanguageDAO extends GenericDAO<LanguageEntity> {
    public LanguageDAO(SessionFactory sessionFactory) {
        super(LanguageEntity.class, sessionFactory);
    }
}
