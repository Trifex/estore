package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.User;

public interface UsersDAO {
    User[] getAllUsers() throws IOException;
    User getUser(String name) throws IOException;
    User createUser(String name) throws IOException;
}
