package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementGUI extends JFrame {
    private JTextField studentIDField, nameField, ageField, gradeField;
    private JTextArea outputArea;
    private StudentManagerImpl manager;

    public StudentManagementGUI() {
        // Ustawienia okna
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicjalizacja managera studentów
        manager = new StudentManagerImpl();

        // Panel wejściowy
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Panel"));

        // Etykiety i pola tekstowe
        inputPanel.add(new JLabel("Student ID:"));
        studentIDField = new JTextField();
        inputPanel.add(studentIDField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        // Przyciski
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton updateButton = new JButton("Update Student");
        JButton displayButton = new JButton("Display All Students");
        JButton calculateButton = new JButton("Calculate Average");

        // Dodawanie przycisków
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(updateButton);
        inputPanel.add(displayButton);
        inputPanel.add(calculateButton);

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
            String id = studentIDField.getText();
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double grade = Double.parseDouble(gradeField.getText());

            if (age < 0 || grade < 0 || grade > 100) {
                outputArea.setText("Error: Age must be positive and grade must be between 0.0 and 100.0");
                return;
            }

            manager.addStudent(new Student(name, age, grade, id));
            outputArea.setText("Student added successfully!");
        } catch (NumberFormatException ex) {
            outputArea.setText("Error: Invalid input. Please check the age and grade fields.");
        }
    }

    private void removeStudent() {
        String id = studentIDField.getText();
        manager.removeStudent(id);
        outputArea.setText("Student with ID " + id + " removed (if existed).");
    }

    private void updateStudent() {
        String id = studentIDField.getText();
        manager.updateStudent(id);
        outputArea.setText("Student with ID " + id + " updated (if existed).");
    }

    private void displayAllStudents() {
        ArrayList<Student> students = manager.displayAllStudents();
        StringBuilder output = new StringBuilder("All Students:\n");
        for (Student student : students) {
            output.append(student.toString()).append("\n");
        }
        outputArea.setText(output.toString());
    }

    private void calculateAverage() {
        double average = manager.calculateAverageGrade();
        outputArea.setText("Average Grade: " + average);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI gui = new StudentManagementGUI();
            gui.setVisible(true);
        });
    }
}

