package org.example;

import java.util.Date;

public class Student{
    private int studentId;
    private String firstName;
    private String lastName;
    private int age;
    private double grade;

    public Student(String firstName, String lastName, int age, double grade) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
        this.setGrade(grade);
    }

    public Student(int studentId, String firstName, String lastName, int age, double grade) {
        this.setStudentId(studentId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
        this.setGrade(grade);
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.isEmpty()) {
            this.firstName = firstName;
        }else {
            throw new IllegalArgumentException("Pierwsze imię nie może być puste");
        }
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = lastName;
        }else {
            throw new IllegalArgumentException("Nazwisko nie moze byc puste");
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 18 && age <= 100) {
            this.age = age;
        }else {
            throw new IllegalArgumentException("Wiek musi byc liczba calkowita pomiedzy 18 i 100");
        }
    }

    public double getGrade(){
        return grade;
    }

    public void setGrade(double grade) {
        if (grade >= 2.0 && grade <= 6.0){
            this.grade = grade;
        }else {
            throw new IllegalArgumentException("Ocena musi byc w zakresie od 2 do 6");
        }
    }

    public int getStudentId(){
        return studentId;
    }

    public void setStudentId(int studentId){
        this.studentId = studentId;
    }

    public String getInfo() {
        return "\n" + "Imię: " + firstName + "\n" +
                "Nazwisko: " + lastName + "\n" +
                "Wiek: " + age + "\n" +
                "Ocena: " + grade + "\n" +
                "Id studenta: " + studentId + "\n";
    }
}