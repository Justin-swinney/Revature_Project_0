package org.revature.controllers;


import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.revature.models.Dog;
import org.revature.services.DogService;

import java.util.Map;
import java.util.UUID;

public class DogController implements CrudHandler {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @Override
    public void create(@NotNull Context context) {
        try {
            Dog dog = context.bodyAsClass(Dog.class);
            dogService.createDog(dog);
            context.json(dog);
        } catch (IllegalArgumentException e) {
            context.status(400).result(e.getMessage());
        }
    }

    @Override
    public void getAll(@NotNull Context context) {
        context.json(dogService.getAllDogs());
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        Dog dog = dogService.getDogById(UUID.fromString(s));
        if (dog != null) {
            context.json(dog);
        }
        else {
            context.status(404);
        }
    }

    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        try {
            UUID id = UUID.fromString(s);
            Map<String, Object> updates = context.bodyAsClass(Map.class);
            boolean dogUpdated = dogService.updateDog(updates, id);
            if (dogUpdated) {
                context.status(200).result("Dog Information updated Successfully");
            } else {
                context.status(404).result("Update Failed!");
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());;
        }
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        try {
            UUID id = UUID.fromString(s);
            Dog dog = dogService.getDogById(id);
            if (dog == null || dog.getAnimalId() == null) {
                context.status(404).result("Dog Not Found With ID: " + id);
            } else {
                try {
                    dogService.deleteDog(id);
                    context.status(200).result("Dog Deleted Successfully!");
                } catch (Exception e) {
                    context.status(500).result("An Error Occurred while trying to delete Dog");
                }
            }
        } catch (IllegalArgumentException e) {
            context.status(404).result("Invalid ID Format");
        }
    }

    public void getAllDogsByOwnerId(@NotNull Context context) {
        UUID id = UUID.fromString(context.pathParam("owner_id"));
        context.json(dogService.getAllDogsByOwnerId(id));
    }

    public void getDogsByBreed(@NotNull Context context) {
        String breed = context.queryParam("breed");
        if (breed != null) {
            breed = breed.toUpperCase();
        }
        context.json(dogService.getDogsByBreed(breed));
    }
}
