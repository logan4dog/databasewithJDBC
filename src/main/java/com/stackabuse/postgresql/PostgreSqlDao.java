package com.stackabuse.postgresql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSqlDao implements Dao<MYCustomer, Integer> {
    private static final Logger LOGGER =
            Logger.getLogger(PostgreSqlDao.class.getName());
        private final Optional<Connection> connection;

        public PostgreSqlDao() {
            this.connection = JdbcConnection.getConnection();
        }	

        @Override
        public Optional<Integer> save(MYCustomer customer) {
            String message = "The customer to be added should not be null";
            MYCustomer nonNullCustomer = Objects.requireNonNull(customer, message);
            String sql = "INSERT INTO "
                    + "mycustomer(first_name, last_name, email) "
                    + "VALUES(?, ?, ?)";

            return connection.flatMap(conn -> {
                Optional<Integer> generatedId = Optional.empty();

                try (PreparedStatement statement =
                     conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, nonNullCustomer.getFirstName());
                    statement.setString(2, nonNullCustomer.getLastName());
                    statement.setString(3, nonNullCustomer.getEmail());

                    int numberOfInsertedRows = statement.executeUpdate();

                    // Retrieve the auto-generated id
                    if (numberOfInsertedRows > 0) {
                        try (ResultSet resultSet = statement.getGeneratedKeys()) {
                            if (resultSet.next()) {
                                generatedId = Optional.of(resultSet.getInt(1));
                            }
                        }
                    }

                    LOGGER.log(
                        Level.INFO,
                        "{0} created successfully? {1}",
                         new Object[]{nonNullCustomer,
                                (numberOfInsertedRows > 0)});
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }

                return generatedId;
            });
        }
     // Other methods of the interface which currently aren't implemented yet
		@Override
		public Optional<MYCustomer> get(int id) {
			// TODO Auto-generated method stub
		    return connection.flatMap(conn -> {
		        Optional<MYCustomer> customer = Optional.empty();
		        String sql = "SELECT * FROM mycustomer WHERE customer_id = " + id;

		        try (Statement statement = conn.createStatement();
		                ResultSet resultSet = statement.executeQuery(sql)) {

		            if (resultSet.next()) {
		                String firstName = resultSet.getString("first_name");
		                String lastName = resultSet.getString("last_name");
		                String email = resultSet.getString("email");

		                customer = Optional.of(
		                    new MYCustomer(id, firstName, lastName, email));

		                LOGGER.log(Level.INFO, "Found {0} in database", customer.get());
		            }
		        } catch (SQLException ex) {
		            LOGGER.log(Level.SEVERE, null, ex);
		            System.out.format("Problem in DAO %s", "get");
		        }

		        return customer;
		    });
		}

		@Override
		public Collection<MYCustomer> getAll() {
		    Collection<MYCustomer> customers = new ArrayList<>();
		    String sql = "SELECT * FROM mycustomer";

		    connection.ifPresent(conn -> {
		        try (Statement statement = conn.createStatement();
		                ResultSet resultSet = statement.executeQuery(sql)) {

		            while (resultSet.next()) {
		                int id = resultSet.getInt("customer_id");
		                String firstName = resultSet.getString("first_name");
		                String lastName = resultSet.getString("last_name");
		                String email = resultSet.getString("email");

		                MYCustomer customer = new MYCustomer(id, firstName, lastName, email);

		                customers.add(customer);

		                LOGGER.log(Level.INFO, "Found {0} in database", customer);
		            }

		        } catch (SQLException ex) {
		            LOGGER.log(Level.SEVERE, null, ex);
		        }
		    });

		    return customers;
		}

		@Override
		public void update(MYCustomer customer) {
			// TODO Auto-generated method stub
		    String message = "The customer to be updated should not be null";
		    MYCustomer nonNullCustomer = Objects.requireNonNull(customer, message);
		    String sql = "UPDATE mycustomer "
		            + "SET "
		            + "first_name = ?, "
		            + "last_name = ?, "
		            + "email = ? "
		            + "WHERE "
		            + "customer_id = ?";

		    connection.ifPresent(conn -> {
		        try (PreparedStatement statement = conn.prepareStatement(sql)) {

		            statement.setString(1, nonNullCustomer.getFirstName());
		            statement.setString(2, nonNullCustomer.getLastName());
		            statement.setString(3, nonNullCustomer.getEmail());
		            statement.setInt(4, nonNullCustomer.getId());

		            int numberOfUpdatedRows = statement.executeUpdate();

		            LOGGER.log(Level.INFO, "Was the customer updated successfully? {0}",
		                    numberOfUpdatedRows > 0);

		        } catch (SQLException ex) {
		            LOGGER.log(Level.SEVERE, null, ex);
		        }
		    });	
		}

		@Override
		public void delete(MYCustomer customer) {
			// TODO Auto-generated method stub
		    String message = "The customer to be deleted should not be null";
		    MYCustomer nonNullCustomer = Objects.requireNonNull(customer, message);
		    String sql = "DELETE FROM mycustomer WHERE customer_id = ?";

		    connection.ifPresent(conn -> {
		        try (PreparedStatement statement = conn.prepareStatement(sql)) {

		            statement.setInt(1, nonNullCustomer.getId());

		            int numberOfDeletedRows = statement.executeUpdate();

		            LOGGER.log(Level.INFO, "Was the customer deleted successfully? {0}",
		                    numberOfDeletedRows > 0);

		        } catch (SQLException ex) {
		            LOGGER.log(Level.SEVERE, null, ex);
		        }
		    });	
		}        
        
}
