package com.example.api.model;

import java.util.Comparator;
import java.util.Date;

public class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private int salary;
    private int age;
    private Date dateOfJoining;

    public Employee(int id, String name, int salary, int age, Date dateOfJoining) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.dateOfJoining = dateOfJoining;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    @Override
    public int compareTo(Employee other) {
        return this.id - other.id; // Sort by ID by default
    }

}

