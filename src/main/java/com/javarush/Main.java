package com.javarush;

import com.javarush.dao.*;
import com.javarush.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Main {
    private final SessionFactory sessionFactory;
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

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

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();
        CustomerEntity customer = main.createCustomer();
        main.customerReturnInventoryToStore();
        main.customerRentInventory(customer);
        main.newFilmWasMade();

    }

    private void newFilmWasMade() {
        try (Session sessions = sessionFactory.getCurrentSession()) {
            sessions.beginTransaction();

            LanguageEntity language = languageDAO.getItems(0,20).stream().unordered().findAny().get();
            List<CategoryEntity> categories = categoryDAO.getItems(0,5);
            List<ActorEntity> actors = actorDAO.getItems(0,20);

            FilmEntity film = new FilmEntity();
            film.setActors(new HashSet<>(actors));
            film.setRating(Rating.NC17);
            film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
            film.setLength((short) 123);
            film.setReplacementCost(BigDecimal.TEN);
            film.setRentalRate(BigDecimal.ZERO);
            film.setLanguageId(language);
            film.setDescription("new scary film");
            film.setTitle("scary my-movie");
            film.setRentalDuration((byte) 44);
            film.setOriginalLanguageId(language);
            film.setCategories(new HashSet<>(categories));
            film.setReleaseYear(Year.now());
            filmDAO.save(film);

            FilmTextEntity filmText = new FilmTextEntity();
            filmText.setFilm(film);
            filmText.setId(film.getId());
            filmText.setDescription("new scary film");
            filmText.setTitle("scary my-movie");
            filmTextDAO.save(filmText);

            sessions.getTransaction().commit();
        }
    }

    private void customerRentInventory(CustomerEntity customer) {
        try (Session sessions = sessionFactory.getCurrentSession()) {
            sessions.beginTransaction();

            FilmEntity film = filmDAO.getFirstAvailableFilmForRent();
            StoreEntity store = storeDAO.getItems(0, 1).get(0);
            InventoryEntity inventory = new InventoryEntity();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            StaffEntity staff = store.getStaff();

            RentalEntity rental = new RentalEntity();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            PaymentEntity payment = new PaymentEntity();
            payment.setRental(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(55.77));
            payment.setStaff(staff);
            paymentDAO.save(payment);

            sessions.getTransaction().commit();
        }
    }

    private void customerReturnInventoryToStore() {
        try (Session sessions = sessionFactory.getCurrentSession()) {
            sessions.beginTransaction();

            RentalEntity rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());

            rentalDAO.save(rental);

            sessions.getTransaction().commit();
        }
    }

    private CustomerEntity createCustomer() {
        try (Session sessions = sessionFactory.getCurrentSession()) {
            sessions.beginTransaction();
            StoreEntity store = storeDAO.getItems(0, 1).get(0);

            CityEntity city = cityDAO.getByName("Kragujevac");

            AddressEntity address = new AddressEntity();
            address.setAddress("Indep str, 48");
            address.setPhone("999-111-555");
            address.setCityId(city);
            address.setDistrict("Vesele");
            addressDAO.save(address);

            CustomerEntity customer = new CustomerEntity();
            customer.setIsActive(true);
            customer.setEmail("test@gmail.com");
            customer.setAddressId(address);
            customer.setStoreId(store);
            customer.setFirstName("Den");
            customer.setLastName("Syrotiuk");
            customerDAO.save(customer);

            sessions.getTransaction().commit();
            return customer;
        }
    }
}