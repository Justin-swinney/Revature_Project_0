package org.revature.services;

import org.revature.DAOs.DogDAO;
import org.revature.models.Dog;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DogService {

    private final DogDAO dogDAO;
    private final OwnerService ownerService;

    public DogService(DogDAO dogDAO, OwnerService ownerService) {
        this.dogDAO = dogDAO;
        this.ownerService = ownerService;
    }

    public void createDog(Dog dog) {
        if (ownerService.getOwnerById(dog.getOwnerId()) == null) {
            throw new IllegalArgumentException("Owner not found with ID: " + dog.getOwnerId());
        }
        if (dog.getName().isEmpty()) {
            throw new IllegalArgumentException("Give that DAWG a NAME!");
        }
        if (dog.getOwnerId() == null || dog.getOwnerId().toString().isEmpty()) {
            throw new IllegalArgumentException("That DAWG needs a owner!");
        }
        if (dog.getBreed() == null || dog.getBreed().isEmpty() ) {
            throw new IllegalArgumentException("If breed is unknown please type unknown.");
        }
        else {
            dogDAO.create(dog);
        }
    }

    public List<Dog> getAllDogs() {
        return dogDAO.findAll();
    }

    public Dog getDogById(UUID id) {
        return dogDAO.findById(id);
    }

    public boolean updateDog(Map<String, List<String>> updates, UUID id) {
        return dogDAO.updateDog(updates, id);
    }

    public void deleteDog(UUID id) {
        dogDAO.delete(id);
    }

    public List<Dog> getAllDogsByOwnerId(UUID ownerId) {
       return dogDAO.findAllDogsByOwnerId(ownerId);
    }
    public List<Dog> getDogsByBreed(String breed) {
        return dogDAO.getDogsByBreed(breed);
    }
}
