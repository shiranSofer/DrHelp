package com.example.shiran.drhelp.entities;

public class Translator extends User {
    private String shiftBoard;

    public Translator(String id, String firstName, String lastName, String email,
                      String password, Boolean available, String language) {
        super(id, firstName, lastName, email, password, available, language);
    }

    public void setShiftBoard(String shiftBoard) {
        this.shiftBoard = shiftBoard;
    }

    public String getShiftBoard() {
        return shiftBoard;
    }
}
