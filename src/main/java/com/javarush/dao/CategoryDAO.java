package com.javarush.dao;

import com.javarush.entity.CategoryEntity;
import org.hibernate.SessionFactory;

public class CategoryDAO extends GenericDAO<CategoryEntity> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(CategoryEntity.class, sessionFactory);
    }
}
