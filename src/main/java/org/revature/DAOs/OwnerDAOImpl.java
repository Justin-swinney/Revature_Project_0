package org.revature.DAOs;

import org.revature.models.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OwnerDAOImpl implements OwnerDAO {
    private final Connection connection;

    public OwnerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Owner owner) {
        String query = "INSERT INTO owners (owner_id, first_name, last_name, phone_number, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, owner.getOwnerId());
            preparedStatement.setString(2, owner.getFirstName());
            preparedStatement.setString(3, owner.getLastName());
            preparedStatement.setString(4, owner.getPhoneNumber());
            preparedStatement.setString(5, owner.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public Owner findById(UUID id) {
        String query = "SELECT * FROM owners WHERE owner_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id, Types.OTHER);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return moveResultsToOwner(resultSet);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();
        String query = "SELECT * FROM owners";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                owners.add(moveResultsToOwner(resultSet));
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return owners;
    }

    @Override
    public boolean updateOwner(Map<String, Object> updates, UUID id) {
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
        String query = "DELETE FROM owners WHERE owner_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id, Types.OTHER);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    private Owner moveResultsToOwner(ResultSet resultSet) throws SQLException {
        Owner owner = new Owner();
        owner.setOwnerId(UUID.fromString(resultSet.getString("owner_id")));
        owner.setFirstName(resultSet.getString("first_name"));
        owner.setLastName(resultSet.getString("last_name"));
        owner.setPhoneNumber(resultSet.getString("phone_number"));
        owner.setEmail(resultSet.getString("email"));
        return owner;
    }

    private String createUpdateQuery(Map<String, Object> updates) {
        StringBuilder query = new StringBuilder("UPDATE owners SET ");
        for (String key : updates.keySet()) {
            query.append(key).append(" = ?, ");
        }
        query.setLength(query.length() - 2);
        query.append(" WHERE ").append("owner_id").append(" = ?");
        return query.toString();
    }
}