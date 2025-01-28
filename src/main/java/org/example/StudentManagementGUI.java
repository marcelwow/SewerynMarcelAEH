package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementGUI extends JFrame {
    private JTextField studentIdField, firstNameField, lastNameField, ageField, gradeField;
    private JTextArea outputArea;
    private StudentManagerImpl manager;

    public StudentManagementGUI() {
        // Ustawienia okna
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicjalizacja managera studentów
        manager = new StudentManagerImpl();

        // Panel wejściowy
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Panel"));

        // Panel dla pól formularza
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 10)); // 5 rzędów dla 5 par label-pole

        // Przyciski
        JButton addButton = new JButton("Dodaj studenta");
        JButton removeButton = new JButton("Usuń studenta");
        JButton updateButton = new JButton("Zaktualizuj studenta");
        JButton displayButton = new JButton("Wyświetl wszystkich studentów");
        JButton calculateButton = new JButton("Oblicz średnią ocen");

        studentIdField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        ageField = new JTextField();
        gradeField = new JTextField();

        // Dodawanie etykiet i pól tekstowych do formPanel
        formPanel.add(new JLabel("Student Id:"));
        formPanel.add(studentIdField);

        formPanel.add(new JLabel("Imię:"));
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Nazwisko:"));
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Wiek:"));
        formPanel.add(ageField);

        formPanel.add(new JLabel("Ocena:"));
        formPanel.add(gradeField);

        // Panel dla przycisków
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(calculateButton);

        // Dodawanie paneli do głównego panelu wejściowego
        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel wyjściowy
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output Panel"));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Dodanie paneli do głównego okna
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        // Obsługa zdarzeń
        addButton.addActionListener(e -> addStudent());
        removeButton.addActionListener(e -> removeStudent());
        updateButton.addActionListener(e -> updateStudent());
        displayButton.addActionListener(e -> displayAllStudents());
        calculateButton.addActionListener(e -> calculateAverage());
    }

    private void addStudent() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double grade = getGrade(age);

            manager.addStudent(new Student(firstName, lastName, age, grade));
            outputArea.setText("Dodanie studenta zakończone powodzeniem");

            cleanUpInputFields();
        } catch (IllegalArgumentException ex) {
            outputArea.setText(ex.toString());
        }
    }

    private double getGrade(int age) {
        double grade = Double.parseDouble(gradeField.getText());

        if ((age < 18 || age > 100) && (grade < 2.0 || grade > 6.0)) {
            throw new IllegalArgumentException("Błąd: Wiek musi być pomiędzy 18 a 100, oraz ocena musi być pomiędzy 2.0 i 6.0");
        }
        else if (age < 18 || age > 100){
            throw new IllegalArgumentException("Błąd: Wiek musi być pomiędzy 18 a 100");
        }
        else if (grade < 2.0 || grade > 6.0){
            throw new IllegalArgumentException("Błąd: Ocena musi być pomiędzy 2.0 i 6.0");
        }
        return grade;
    }

    private void removeStudent() {
        int studentId = Integer.parseInt(studentIdField.getText());
        manager.removeStudent(studentId);
        outputArea.setText("Student with ID " + studentId + " removed (if existed).");
        cleanUpInputFields();
    }

    private void updateStudent() {
        int studentId = Integer.parseInt(studentIdField.getText());
        Student student = manager.getStudent(studentId);

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        double grade = getGrade(age);

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAge(age);
        student.setGrade(grade);

        manager.updateStudent(student);
        outputArea.setText("Student with ID " + studentId + " updated (if existed).");
        cleanUpInputFields();
    }

    private void displayAllStudents() {
        ArrayList<Student> students = manager.getAllStudents();
        StringBuilder output = new StringBuilder("All Students:\n");
        for (Student student : students) {
            output.append(student.getInfo()).append("\n");
        }
        outputArea.setText(output.toString());
    }

    private void calculateAverage() {
        double average = manager.calculateAverageGrade();
        outputArea.setText("Average Grade: " + average);
    }

    private void cleanUpInputFields(){
        studentIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        gradeField.setText("");
    }
}

