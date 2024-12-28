package org.example;

import java.util.ArrayList;

public interface student_manager {
    void addStudent(Student student);
    void removeStudent(String studentID);
    void updateStudent(String studentID);
    ArrayList<Student> displayAllStudents();
    double calculateAvargeGrade();
}
