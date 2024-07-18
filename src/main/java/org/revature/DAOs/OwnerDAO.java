package org.revature.DAOs;

import org.revature.models.Owner;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OwnerDAO {
    void create(Owner owner);
    Owner findById(UUID id);
    List<Owner> findAll();
    boolean updateOwner(Map<String, Object> updates, UUID id);
    void delete(UUID id);
}
