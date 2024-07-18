package org.revature.services;

import org.revature.DAOs.OwnerDAO;
import org.revature.models.Owner;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OwnerService {
    private final OwnerDAO ownerDAO;
    public OwnerService(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    public void createOwner(Owner owner) {
        ownerDAO.create(owner);
    }

    public List<Owner> getAllOwners() {
        return ownerDAO.findAll();
    }

    public Owner getOwnerById(UUID id) {
        return ownerDAO.findById(id);
    }

    public boolean updateOwner(Map<String, Object> updates, UUID id) {
        return ownerDAO.updateOwner(updates, id);
    }

    public void deleteOwner(UUID id) {
        ownerDAO.delete(id);
    }
}
