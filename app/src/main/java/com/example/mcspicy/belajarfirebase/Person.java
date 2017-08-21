package com.example.mcspicy.belajarfirebase;

/**
 * Created by mcspicy on 21/8/17.
 */

public class Person {
    private String name;
    private String status;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
