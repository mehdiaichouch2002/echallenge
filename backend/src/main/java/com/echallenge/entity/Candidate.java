package com.echallenge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "candidates")
@PrimaryKeyJoinColumn(name = "id")
public class Candidate extends User {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String school;

    private String field;

    @Column(nullable = false)
    private String gsm;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private boolean confirmed = false;

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
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}
