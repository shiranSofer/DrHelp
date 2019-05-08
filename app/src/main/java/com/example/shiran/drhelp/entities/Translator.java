package com.example.shiran.drhelp.entities;

import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.HashMap;
import java.util.Map;

public class Translator extends User {

    private HashMap<DatePicker, TimePicker> shiftsBoard;

    public Translator(String id, String firstName, String lastName, String email,
                      String password, Boolean available, String language, HashMap<DatePicker,TimePicker>shiftBoard) {

        super(id, firstName, lastName, email, password, available, language);
        shiftBoard = new HashMap<>();
        setShiftsBoard(shiftBoard);
    }

    public void setShiftsBoard(HashMap<DatePicker, TimePicker> shiftsBoard) {
        this.shiftsBoard = shiftsBoard;
    }

    public Map<DatePicker, TimePicker> getShiftsBoard() {
        return shiftsBoard;
    }
}
