package org.example;

import java.util.ArrayList;

    public interface StudentManager {
    void addStudent(Student student);
    void removeStudent(int studentId);
    void updateStudent(Student student);
    Student getStudent(int studentId);
    ArrayList<Student> getAllStudents();
    double calculateAverageGrade();
    }
