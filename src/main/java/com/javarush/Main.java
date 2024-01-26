package com.javarush;

import com.javarush.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class Main {
    private final SessionFactory sessionFactory;

    public Main() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "Den23011990@");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");


        sessionFactory = new Configuration()
                .addAnnotatedClass(ActorEntity.class)
                .addAnnotatedClass(AddressEntity.class)
                .addAnnotatedClass(CategoryEntity.class)
                .addAnnotatedClass(CityEntity.class)
                .addAnnotatedClass(CountryEntity.class)
                .addAnnotatedClass(CustomerEntity.class)
                .addAnnotatedClass(FilmEntity.class)
                .addAnnotatedClass(FilmTextEntity.class)
                .addAnnotatedClass(InventoryEntity.class)
                .addAnnotatedClass(LanguageEntity.class)
                .addAnnotatedClass(PaymentEntity.class)
                .addAnnotatedClass(RentalEntity.class)
                .addAnnotatedClass(StaffEntity.class)
                .addAnnotatedClass(StoreEntity.class)

                .addProperties(properties)
                .buildSessionFactory();
    }

    public static void main(String[] args) {

        Main main = new Main();

    }
}