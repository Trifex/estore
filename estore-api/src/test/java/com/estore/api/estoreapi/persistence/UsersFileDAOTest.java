package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.estore.api.estoreapi.model.Coupon;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class UsersFileDAOTest {
    UsersFileDAO usersFileDAO;
    ObjectMapper mockObjectMapper;

    private User[] testUsers = {
        new User("user1"),
        new User("user2"),
        new User("user3")
    };

    @BeforeEach
    public void setupCouponFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the coupon array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), User[].class))
                .thenReturn(testUsers);

        usersFileDAO = new UsersFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetAllUsers() throws IOException {
        User[] result = usersFileDAO.getAllUsers();

        for(int i = 0; i < result.length; i++) {
            assertEquals(testUsers[i], result[i]);
        }
    }

    @Test
    public void testUpdateUser() throws IOException {
        // Setup
        User user = new User("user1");

        // Invoke
        User result = usersFileDAO.updateUser(user);

        // Analyze
        assertNotNull(result);
    }

    @Test
    public void testUpdateUserNotFound() throws IOException {
        // Setup
        User user = new User("user4");

        // Invoke
        User result = usersFileDAO.updateUser(user);

        // Analyze
        assertNull(result);
    }
}
