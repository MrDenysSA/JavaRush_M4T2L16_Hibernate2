package org.h2;

import com.javarush.dao.*;
import com.javarush.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Driver {
    private static SessionFactory sessionFactory = null;
    private Session session = null;
    private final ActorDAO actorDAO = new ActorDAO(sessionFactory);
    private final AddressDAO addressDAO = new AddressDAO(sessionFactory);
    private final CategoryDAO categoryDAO = new CategoryDAO(sessionFactory);
    private final CityDAO cityDAO = new CityDAO(sessionFactory);
    private final CountryDAO countryDAO = new CountryDAO(sessionFactory);
    private final CustomerDAO customerDAO = new CustomerDAO(sessionFactory);
    private final FilmDAO filmDAO = new FilmDAO(sessionFactory);
    private final FilmTextDAO filmTextDAO = new FilmTextDAO(sessionFactory);
    private final InventoryDAO inventoryDAO = new InventoryDAO(sessionFactory);
    private final LanguageDAO languageDAO = new LanguageDAO(sessionFactory);
    private final PaymentDAO paymentDAO = new PaymentDAO(sessionFactory);
    private final RentalDAO rentalDAO = new RentalDAO(sessionFactory);
    private final StaffDAO staffDAO = new StaffDAO(sessionFactory);
    private final StoreDAO storeDAO = new StoreDAO(sessionFactory);

    @BeforeAll
    static void setup() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate-test.cfg.xml").build();

            Metadata metadata = new MetadataSources(standardRegistry)
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
                    .getMetadataBuilder()
                    .build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @BeforeEach
    void setupThis() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearThis() {
        session.getTransaction().commit();
    }

    @AfterAll
    static void tear() {
        sessionFactory.close();
    }

    @Test
    public void newFilmWasMade() {
        LanguageEntity language = languageDAO.getItems(0, 20).stream().unordered().findAny().get();
        List<CategoryEntity> categories = categoryDAO.getItems(0, 5);
        List<ActorEntity> actors = actorDAO.getItems(0, 20);

        FilmEntity film = new FilmEntity();
        film.setActors(new HashSet<>(actors));
        film.setRating(Rating.NC17);
        film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
        film.setLength((short) 123);
        film.setReplacementCost(BigDecimal.TEN);
        film.setRentalRate(BigDecimal.ZERO);
        film.setLanguageId(language);
        film.setDescription("my favorite movie");
        film.setTitle("Home Alone");
        film.setRentalDuration((byte) 44);
        film.setOriginalLanguageId(language);
        film.setCategories(new HashSet<>(categories));
        film.setReleaseYear(Year.now());
        filmDAO.save(film);

        FilmTextEntity filmText = new FilmTextEntity();
        filmText.setFilm(film);
        filmText.setId(film.getId());
        filmText.setDescription("my favorite movie");
        filmText.setTitle("Home Alone");
        filmTextDAO.save(filmText);
    }

    @Test
    public void customerRentInventory(CustomerEntity customer) {
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
    }

    @Test
    public void customerReturnInventoryToStore() {
            RentalEntity rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);
    }

    @Test
    public CustomerEntity createCustomer() {
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
            return customer;
    }
}
