package com.stackabuse.postgresql;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collection;

public class App {
    private static final Logger LOGGER =
            Logger.getLogger(App.class.getName());
        private static final Dao<MYCustomer, Integer> CUSTOMER_DAO = new PostgreSqlDao();

        public static void main(String[] args) {
            // Test whether an exception is thrown when
            // the database is queried for a non-existent customer.
            // But, if the customer does exist, the details will be printed
            // on the console
            try {
                MYCustomer customer = getCustomer(1);
            } catch (NonExistentEntityException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage());
            }

            // Test whether a customer can be added to the database
            //MYCustomer firstCustomer =
            //    new MYCustomer("Manuel", "Kelley", "ManuelMKelley@jourrapide.com");
            //MYCustomer secondCustomer =
            //    new MYCustomer("Joshua", "Daulton", "JoshuaMDaulton@teleworm.us");
            //MYCustomer thirdCustomer =
            //    new MYCustomer("April", "Ellis", "AprilMellis@jourrapide.com");
            //addCustomer(firstCustomer).ifPresent(firstCustomer::setId);
            //addCustomer(secondCustomer).ifPresent(secondCustomer::setId);
            //addCustomer(thirdCustomer).ifPresent(thirdCustomer::setId);

            // Test whether the new customer's details can be edited
            //firstCustomer.setFirstName("Franklin");
            //firstCustomer.setLastName("Hudson");
            //firstCustomer.setEmail("FranklinLHudson@jourrapide.com");
            //updateCustomer(firstCustomer);

            // Test whether all customers can be read from database
            getAllCustomers().forEach(System.out::println);

            // Test whether a customer can be deleted
            //deleteCustomer(secondCustomer);
            //deleteCustomer(firstCustomer);
        }

        // Static helper methods referenced above
        public static MYCustomer getCustomer(int id) throws NonExistentEntityException {
            Optional<MYCustomer> customer = CUSTOMER_DAO.get(id);
            return customer.orElseThrow(NonExistentCustomerException::new);
        }

        public static Collection<MYCustomer> getAllCustomers() {
            return CUSTOMER_DAO.getAll();
        }

        public static void updateCustomer(MYCustomer customer) {
            CUSTOMER_DAO.update(customer);
        }

        public static Optional<Integer> addCustomer(MYCustomer customer) {
            return CUSTOMER_DAO.save(customer);
        }

        public static void deleteCustomer(MYCustomer customer) {
            CUSTOMER_DAO.delete(customer);
        }

}
