package com.javarush;

import com.javarush.entity.*;

public class Main {
    public static void main(String[] args) {
        MovieDB movieDB = new MovieDB();
        CustomerEntity customer = movieDB.createCustomer();
        movieDB.customerReturnInventoryToStore();
        movieDB.customerRentInventory(customer);
        movieDB.newFilmWasMade();
    }
}