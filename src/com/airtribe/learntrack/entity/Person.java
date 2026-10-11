package com.airtribe.learntrack.entity;

/** Base class for people in the system (students, trainers). */
public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    /** Overridden by subclasses to show role-specific text (polymorphism). */
    public String getDisplayName() {
        return getFullName();
    }
}
