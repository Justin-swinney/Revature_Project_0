package org.revature;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import org.revature.DAOs.DogDAOImpl;
import org.revature.DAOs.OwnerDAOImpl;
import org.revature.controllers.DogController;
import org.revature.controllers.OwnerController;
import org.revature.services.DogService;
import org.revature.services.OwnerService;
import org.revature.utils.JdbcUtils;

import java.sql.SQLException;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Main {

    public static void main(String[] args) {
        Javalin.create(javalinConfig -> javalinConfig.router.apiBuilder(() -> path("api", () -> {
            path("owners", Main::getOwnerEndpoint);
            path("dogs", Main::getDogEndpoint);
        }))).start(7070);
    }

    private static void getOwnerEndpoint() {
        try {
            OwnerDAOImpl ownerDAO = new OwnerDAOImpl(JdbcUtils.getConnection());
            OwnerService ownerService = new OwnerService(ownerDAO);
            crud("{id}", new OwnerController(ownerService));
        } catch (SQLException sqlException) {
            System.out.println("ERROR @ Owner Endpoint Method: " + sqlException.getMessage());
        }
    }
    private static void getDogEndpoint() {
        try {
            DogDAOImpl dogDAO = new DogDAOImpl(JdbcUtils.getConnection());
            OwnerDAOImpl ownerDAO = new OwnerDAOImpl(JdbcUtils.getConnection());
            DogService dogService = new DogService(dogDAO, new OwnerService(ownerDAO));
            DogController dogController = new DogController(dogService);
            ApiBuilder.get("byBreed", dogController::getDogsByBreed);
            crud("{id}", new DogController(dogService));
            ApiBuilder.get("owned-dogs/{owner_id}", dogController::getAllDogsByOwnerId); // Custom endpoint for getting all items by user ID.
        } catch (SQLException sqlException) {
            System.out.println("ERROR @ Dog Endpoint Method: " + sqlException.getMessage());
        }
    }

}