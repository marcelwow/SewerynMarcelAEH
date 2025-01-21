package org.example;

import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {
    private ArrayList<Student> students = new ArrayList<>();

    @Override
    public void addStudent(Student student){
        students.add(student);
        System.out.println("Dodano studenta: " + student.getName());
    }

    @Override
    public void removeStudent(String studentID) {
        boolean found = false;
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                students.remove(student);
                System.out.println("Usunięto studenta o ID: " + studentID);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student o ID " + studentID + " nie został znaleziony.");
        }
    }

    @Override
    public void updateStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                // Wprowadź zmiany - dla przykładu zmienimy tylko ocenę
                student.setGrade(student.getGrade() + 5); // Przykład: dodajemy 5 punktów do oceny
                System.out.println("Zaktualizowano dane studenta o ID: " + studentID);
                return;
            }
        }
        System.out.println("Student o ID " + studentID + " nie został znaleziony.");
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        return students;
    }

    @Override
    public double calculateAverageGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Student student : students) {
            total += student.getGrade();
        }
        return total / students.size();
    }
}
