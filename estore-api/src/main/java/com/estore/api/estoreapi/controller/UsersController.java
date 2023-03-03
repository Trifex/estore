package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UsersDAO;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("users")
public class UsersController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private UsersDAO usersDAO;

    public UsersController(UsersDAO usersDAO){
        this.usersDAO = usersDAO;
    }

    @GetMapping("")
    public ResponseEntity<User[]> getAllUsers() {
        LOG.info("GET /users");
        try {
            User[] users = usersDAO.getAllUsers();
            return new ResponseEntity<User[]>(users, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name) {
        LOG.info("GET /users/" + name);
        try {
            User user = usersDAO.getUser(name);
            if (user != null)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody String name){
        LOG.info("POST /user " + name);
        try{
            User createdUser = usersDAO.createUser(name);
            if(createdUser == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
