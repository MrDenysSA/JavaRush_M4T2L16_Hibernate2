package org.h2;


import org.h2.engine.*;
import org.h2.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;



public class Driver {
    @Test
    public class HelloTest {
        private static SessionFactory sessionFactory = null;
        private Session session = null;

        @BeforeAll
        static void setup(){
            try {
                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure("hibernate-test.cfg.xml").build();

                Metadata metadata = new MetadataSources(standardRegistry)
                        .addAnnotatedClass(Employee.class)
                        .getMetadataBuilder()
                        .build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }

        @BeforeEach
        void setupThis(){
            session = sessionFactory.openSession();
            session.beginTransaction();
        }

        @AfterEach
        void tearThis(){
            session.getTransaction().commit();
        }

        @AfterAll
        static void tear(){
            sessionFactory.close();
        }
}
