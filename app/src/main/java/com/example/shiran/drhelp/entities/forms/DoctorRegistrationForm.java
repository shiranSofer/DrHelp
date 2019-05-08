package com.example.shiran.drhelp.entities.forms;

import com.example.shiran.drhelp.entities.Doctor;
import com.example.shiran.drhelp.entities.User;

public class DoctorRegistrationForm extends RegistrationForm {
    private String licenseNumber;

    public DoctorRegistrationForm(String firstName,
                                  String lastName,
                                  String email,
                                  String password,
                                  String licenseNumber
                                  ) {
        super(firstName, lastName, email, password);
        setLicenseNumber(licenseNumber);
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public Doctor toUser(String id) {
        User user = super.toUser(id);
        return new Doctor(id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getAvailable(),
                user.getLanguage(),
                licenseNumber);
    }


    @Override
    public String toString() {
        return "DoctorRegistrationForm{" +
                "licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}
