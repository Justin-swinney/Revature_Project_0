package org.revature.controllers;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.revature.models.Owner;
import org.revature.services.OwnerService;

import java.util.Map;
import java.util.UUID;

public class OwnerController implements CrudHandler {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @Override
    public void create(@NotNull Context context) {
        Owner owner = context.bodyAsClass(Owner.class);
        ownerService.createOwner(owner);
        context.status(201).json(owner);
    }

    @Override
    public void getAll(@NotNull Context context) {
        context.json(ownerService.getAllOwners());
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        Owner owner = ownerService.getOwnerById(UUID.fromString(s));
        if (owner != null) {
            context.json(owner);
        } else {
            context.status(404);
        }
    }

    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        try {
            UUID id = UUID.fromString(s);
            Map<String, Object> updates = context.bodyAsClass(Map.class);
            boolean ownerUpdated = ownerService.updateOwner(updates, id);
            if (ownerUpdated) {
                context.status(200).result("Owner Information updated Successfully");
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
            Owner owner = ownerService.getOwnerById(id);
            if (owner == null || owner.getOwnerId() == null) {
                context.status(404).result("Owner Not Found With ID: " + id);
            } else {
                try {
                    ownerService.deleteOwner(id);
                    context.status(200).result("Owner Deleted Successfully!");
                } catch (Exception e) {
                    context.status(500).result("An Error Occurred while trying to delete Owner");
                }
            }
        } catch (IllegalArgumentException e) {
            context.status(404).result("Invalid ID Format");
        }
    }
}
