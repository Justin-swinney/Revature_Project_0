package org.revature.models;

import java.util.UUID;

public class Owner {
    private UUID ownerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Owner() {
        ownerId = UUID.randomUUID();
    }

    public Owner(UUID ownerId, String firstName, String lastName, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
