package com.example.shiran.drhelp.entities;

import java.util.HashMap;
import java.util.Map;

public class Translator extends User {
    private Map<String,String> shiftBoard; //<id,Day+ShiftTime>

    public Translator(String id, String firstName, String lastName, String email,
                      String password, Boolean available, String language, HashMap<String,String>shiftBoard) {

        super(id, firstName, lastName, email, password, available, language);
        shiftBoard = new HashMap<>();
        setShiftBoard(shiftBoard);
    }

    public void setShiftBoard(HashMap<String,String> shiftBoard) {
        this.shiftBoard = shiftBoard;
    }

    public Map<String, String> getShiftBoard() {
        return shiftBoard;
    }
}
