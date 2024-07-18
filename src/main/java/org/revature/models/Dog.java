package org.revature.models;

import java.util.UUID;

public class Dog {

    private UUID animalId;
    private String name;
    private int age;
    private double weight;
    private String gender;
    private String color;
    private String breed;
    // FK
    private UUID ownerId;

    public Dog() {
        animalId = UUID.randomUUID();
    }

    public Dog(UUID animalId, String name, int age, double weight, String gender, String color, String breed, UUID ownerId) {
        this.animalId = animalId;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.color = color;
        this.breed = breed.toUpperCase();
        this.ownerId = ownerId;
    }

    public UUID getAnimalId() {
        return animalId;
    }

    public void setAnimalId(UUID animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
