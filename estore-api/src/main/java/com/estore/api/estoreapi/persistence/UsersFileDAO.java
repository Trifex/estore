package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UsersFileDAO implements UsersDAO{

    Map<String, User> users;
    private ObjectMapper objectMapper;
    private String filename;

    public UsersFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    @Override
    public User[] getAllUsers() {
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            userArrayList.add(user);
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    private boolean save() throws IOException {
        User[] usersArray = getAllUsers();
        objectMapper.writeValue(new File(filename),usersArray);
        load();
        return true;
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        for (User user : userArray) {
            users.put(user.getName(), user);
        }
        return true;
    }

    @Override
    public User createUser(String name) throws IOException {
        synchronized(users){
            User newUser = new User(name);
            users.put(name, newUser);
            save();
            return newUser;
        }
    }

    @Override
    public User getUser(String username) throws IOException {
        return users.get(username); // Returns null if no user found
    }

    public User updateUser(User user) throws IOException {
        User updated = users.replace(user.getName(), user) != null ? user : null;
        save();
        return updated;
    }
}