package org.revature.DAOs;

import org.revature.models.Dog;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DogDAO {
    void create(Dog dog);
    Dog findById(UUID id);
    List<Dog> findAll();
    boolean updateDog(Map<String, List<String>> updates, UUID id);
    void delete(UUID id);
    List<Dog> findAllDogsByOwnerId(UUID ownerId);
    List<Dog> getDogsByBreed(String breed);
}
