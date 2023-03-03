package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UsersDAO;

@Tag("Controller-tier")
public class UsersControllerTest {
    private UsersController usersController;
    private UsersDAO mockUsersDAO;
    
    @BeforeEach
    public void setupUsersController() {
        mockUsersDAO = mock(UsersDAO.class);
        usersController = new UsersController(mockUsersDAO);
    }

    @Test
    public void testGetAllUsers() throws IOException {
        User[] users = {
            new User("hugh_janus"), 
            new User("barry_mckockiner")
        };
        when(mockUsersDAO.getAllUsers()).thenReturn(users);

        ResponseEntity<User[]> response = usersController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUser() throws IOException {
        User user = new User("mike_hunt");

        when(mockUsersDAO.getUser(user.getName())).thenReturn(user);

        ResponseEntity<User> response = usersController.getUser(user.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws IOException {
        String username = "ben_dover";

        when(mockUsersDAO.getUser(username)).thenReturn(null);

        ResponseEntity<User> response = usersController.getUser(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws IOException {
        User user = new User("jenna_talia");

        when(mockUsersDAO.createUser(user.getName())).thenReturn(user);

        ResponseEntity<User> response = usersController.createUser(user.getName());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException {
        User user = new User("phil_mckraken");

        when(mockUsersDAO.createUser(user.getName())).thenReturn(null);

        ResponseEntity<User> response = usersController.createUser(user.getName());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}