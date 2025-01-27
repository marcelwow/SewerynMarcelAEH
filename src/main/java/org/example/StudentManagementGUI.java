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
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Panel"));

        // Etykiety i pola tekstowe
        inputPanel.add(new JLabel("Student Id:"));
        studentIdField = new JTextField();
        inputPanel.add(studentIdField);

        inputPanel.add(new JLabel("Imię:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Nazwisko:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("Wiek:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Ocena:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        // Przyciski
        JButton addButton = new JButton("Dodaj studenta");
        JButton removeButton = new JButton("Usuń studenta");
        JButton updateButton = new JButton("Zaktualizuj studenta");
        JButton displayButton = new JButton("Wyświetl wszystkich studentów");
        JButton calculateButton = new JButton("Oblicz średnią ocen");

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
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double grade = getGrade(age);

            manager.addStudent(new Student(firstName, lastName, age, grade));
            outputArea.setText("Dodanie studenta zakończone powodzeniem");

            firstNameField.setText("");
            lastNameField.setText("");
            ageField.setText("");
            gradeField.setText("");
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
    }

    private void updateStudent() {
        int studentId = Integer.parseInt(studentIdField.getText());
        Student student = manager.getStudent(studentId);
        manager.updateStudent(student);
        outputArea.setText("Student with ID " + studentId + " updated (if existed).");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI gui = new StudentManagementGUI();
            gui.setVisible(true);
        });
    }
}

