package org.example;

public class Student{
    private String name;
    private int age;
    private double grade;
    private String studentID;
    public Student(String name, int age, double grade, String studentID){
        this.setName(name);
        this.setAge(age);
        this.setGrade(grade);
        this.setStudentID(studentID);
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }else {
            throw new IllegalArgumentException("Imie nie moze byc puste");
        }
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }else {
            throw new IllegalArgumentException("Wiek musi byc liczba dodatnia calkowita");
        }
    }
    public double getGrade(){
        return grade;
    }
    public void setGrade(double grade) {
        if (grade >= 0.0 && grade <= 100.0){
            this.grade = grade;
        }else {
            throw new IllegalArgumentException("Ocena musi byc w zakresie od 0  do 100");
        }
    }
    public String getStudentID(){
        return studentID;
    }
    public void setStudentID(String studentID){
        if (studentID != null && !studentID.isEmpty()) {
            this.studentID = studentID;
        }else {
            throw new IllegalArgumentException("ID studenta nie moze byc puste");
        }
    }

}