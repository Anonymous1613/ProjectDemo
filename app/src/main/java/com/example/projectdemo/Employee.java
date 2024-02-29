package com.example.projectdemo;

public class Employee {
    String name,email,role;
    int id;

    public Employee() {
    }

    public Employee(String name, String email,String role, int id) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {return role;}

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
