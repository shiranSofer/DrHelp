package com.example.shiran.drhelp.entities;

public class Translator extends User {

    public Translator(){}

    public Translator(String id, String firstName, String lastName, String email,
                      String password, Boolean available, String language) {

        super(id, firstName, lastName, email, password, available, language);
    }



}
