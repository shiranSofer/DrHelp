package com.example.shiran.drhelp.entities.forms;

import java.util.HashMap;
import java.util.Map;

public class TranslatorRegistrationForm extends RegistrationForm {

    private Map<String,String> shiftBoard;

    public TranslatorRegistrationForm(String firstName, String lastName, String email, String password, HashMap<String,String> shiftTable) {

        super(firstName, lastName, email, password);
        setShiftBoard(shiftTable);
    }

    public Map<String, String> getShiftBoard() {
        return shiftBoard;
    }

    public void setShiftBoard(Map<String, String> shiftBoard) {
        this.shiftBoard = shiftBoard;
    }
}
