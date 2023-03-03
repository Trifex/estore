package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private static final String STRING_FORMAT = "User [name = %s]";

    @JsonProperty("name") private String name;

    public User(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format(STRING_FORMAT, this.name);
    }
}
