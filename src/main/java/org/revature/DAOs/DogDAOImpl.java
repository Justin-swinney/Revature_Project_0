package org.revature.DAOs;

import org.revature.models.Dog;

import java.sql.*;
import java.util.*;

public class DogDAOImpl implements DogDAO {
    private final Connection connection;

    public DogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Dog dog) {
        String query = "INSERT INTO dogs (animal_id, name, age, weight, gender, color, breed, owner_id_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, dog.getAnimalId());
            preparedStatement.setString(2, dog.getName());
            preparedStatement.setInt(3, dog.getAge());
            preparedStatement.setDouble(4, dog.getWeight());
            preparedStatement.setString(5, dog.getGender());
            preparedStatement.setString(6, dog.getColor());
            preparedStatement.setString(7, dog.getBreed().toUpperCase());
            preparedStatement.setObject(8, dog.getOwnerId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public Dog findById(UUID id) {
        String query = "SELECT * FROM dogs WHERE animal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id, Types.OTHER);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return moveResultsToDog(resultSet);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Dog> findAll() {
        List<Dog> dogs = new ArrayList<>();
        String query = "SELECT * FROM dogs";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                dogs.add(moveResultsToDog(resultSet));
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return dogs;
    }

    @Override
    public boolean updateDog(Map<String, Object> updates, UUID id) {
        String query = createUpdateQuery(updates);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int i = 1;
            for (Object value : updates.values()) {
                preparedStatement.setObject(i++, value);
            }
            preparedStatement.setObject(i, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return false;
        }
    }

    @Override
    public void delete(UUID id) {
        String query = "DELETE FROM dogs WHERE animal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id, Types.OTHER);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public List<Dog> findAllDogsByOwnerId(UUID ownerId) {
        List<Dog> dogs = new ArrayList<>();
        String query = "SELECT * FROM dogs WHERE owner_id_fk = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, ownerId, Types.OTHER);
            try (ResultSet resultSet = preparedStatement .executeQuery()) {
                while (resultSet.next()) {
                    dogs.add(moveResultsToDog(resultSet));

                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return dogs;
    }

    @Override
    public List<Dog> getDogsByBreed(String breed) {
        List<Dog> dogs = new ArrayList<>();
        String query = "SELECT * FROM dogs WHERE breed = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, breed);
            try (ResultSet resultSet = preparedStatement .executeQuery()) {
                while (resultSet.next()) {
                    dogs.add(moveResultsToDog(resultSet));
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return dogs;
    }

    private Dog moveResultsToDog(ResultSet resultSet) throws SQLException {
        Dog dog = new Dog();
        dog.setAnimalId(UUID.fromString(resultSet.getString("animal_id")));
        dog.setName(resultSet.getString("name"));
        dog.setAge(resultSet.getInt("age"));
        dog.setWeight(resultSet.getDouble("weight"));
        dog.setGender(resultSet.getString("gender"));
        dog.setColor(resultSet.getString("color"));
        dog.setBreed(resultSet.getString("breed"));
        dog.setOwnerId(UUID.fromString(resultSet.getString("owner_id_fk")));
        return dog;
    }
    private String createUpdateQuery(Map<String, Object> updates) {
        StringBuilder query = new StringBuilder("UPDATE dogs SET ");
        for (String key : updates.keySet()) {
            query.append(key).append(" = ?, ");
        }
        query.setLength(query.length() - 2);
        query.append(" WHERE ").append("animal_id").append(" = ?");
        return query.toString();
    }

}

