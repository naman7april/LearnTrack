package com.airtribe.learntrack.entity;

/** A student. Demonstrates inheritance (extends Person) and constructor overloading. */
public class Student extends Person {

    private String batch;
    private boolean active;

    public Student() {
        super();
        this.active = true;
    }

    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    /** Overloaded constructor: student without an email address. */
    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, "not provided", batch);
    }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String getDisplayName() {
        return "Student: " + getFullName() + " (" + batch + ")";
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | batch %s | %s",
                getId(), getFullName(), getEmail(), batch, active ? "ACTIVE" : "INACTIVE");
    }
}
