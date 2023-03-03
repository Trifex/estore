package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag ("Model-tier")
public class UserTest {
    @Test
    public void testCreateUser() throws IOException{
        User user = new User("username");
        String result = user.toString();
        String expected = "User [name = username]";
        assertEquals(expected, result);
    }

    @Test
    public void testGetName() throws IOException{
        User user = new User("username");
        String result = user.getName();
        String expected = "username";
        assertEquals(expected, result);
    }

    @Test
    public void testSetName() throws IOException{
        User user = new User("username");
        user.setName("username2");
        String result = user.getName();
        String expected = "username2";
        assertEquals(expected, result);
    }
}