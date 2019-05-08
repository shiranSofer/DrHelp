package com.example.shiran.drhelp.entities;

public class Doctor extends User {
    private String licenseNumber;

    public Doctor(String id, String firstName, String lastName, String email,
                  String password, Boolean available, String language, String licenseNumber) {
        super(id, firstName, lastName, email, password, available, language);
        setLicenseNumber(licenseNumber);
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
