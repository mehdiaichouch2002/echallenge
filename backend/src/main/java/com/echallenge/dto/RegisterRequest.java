package com.echallenge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String school;

    private String field;

    @NotBlank
    private String gsm;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
    public String getGsm() { return gsm; }
    public void setGsm(String gsm) { this.gsm = gsm; }
}
