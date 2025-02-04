package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementGUI extends JFrame {
    private JTextField studentIdField, firstNameField, lastNameField, ageField, gradeField;
    private JTextArea outputArea;
    private StudentManagerImpl manager;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        manager = new StudentManagerImpl();

        Color backgroundColor = Color.decode("#565675");
        Color buttonColor = Color.decode("#10B981");
        Color buttonColor1 = Color.decode("#f70505");
        Color outputColor = Color.decode("#bfa4a4");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Panel"));
        inputPanel.setBackground(backgroundColor);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        formPanel.setBackground(backgroundColor);

        JButton addButton = new JButton("Dodaj studenta");
        JButton removeButton = new JButton("Usuń studenta");
        JButton updateButton = new JButton("Zaktualizuj studenta");
        JButton displayButton = new JButton("Wyświetl wszystkich studentów");
        JButton calculateButton = new JButton("Oblicz średnią ocen");

        addButton.setBackground(buttonColor);
        removeButton.setBackground(buttonColor1);
        updateButton.setBackground(buttonColor);
        displayButton.setBackground(buttonColor);
        calculateButton.setBackground(buttonColor);

        studentIdField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        ageField = new JTextField();
        gradeField = new JTextField();

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

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(removeButton);

        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output Panel"));
        outputPanel.setBackground(backgroundColor);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(outputColor);
        outputArea.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

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
            displaySuccess("Dodanie studenta zakończone powodzeniem");
            cleanUpInputFields();
        } catch (IllegalArgumentException ex) {
            displayError(ex.getMessage());
        }
    }

    private double getGrade(int age) {
        double grade = Double.parseDouble(gradeField.getText());
        if ((age < 18 || age > 100) && (grade < 2.0 || grade > 6.0)) {
            throw new IllegalArgumentException("Błąd: Wiek musi być 18-100, ocena 2.0-6.0");
        } else if (age < 18 || age > 100) {
            throw new IllegalArgumentException("Błąd: Wiek musi być pomiędzy 18 a 100");
        } else if (grade < 2.0 || grade > 6.0) {
            throw new IllegalArgumentException("Błąd: Ocena musi być pomiędzy 2.0 i 6.0");
        }
        return grade;
    }

    private void removeStudent() {
        int studentId = Integer.parseInt(studentIdField.getText());
        manager.removeStudent(studentId);
        displayInfo("Student z ID " + studentId + " został usunięty (jeśli istniał).");
        cleanUpInputFields();
    }

    private void updateStudent() {
        int studentId = Integer.parseInt(studentIdField.getText());
        Student student = manager.getStudent(studentId);
        if (student == null) {
            displayError("Student o podanym ID nie istnieje.");
            return;
        }
        student.setFirstName(firstNameField.getText());
        student.setLastName(lastNameField.getText());
        student.setAge(Integer.parseInt(ageField.getText()));
        student.setGrade(getGrade(student.getAge()));
        manager.updateStudent(student);
        displaySuccess("Student z ID " + studentId + " został zaktualizowany.");
        cleanUpInputFields();
    }

    private void displayAllStudents() {
        ArrayList<Student> students = manager.getAllStudents();
        StringBuilder output = new StringBuilder("Lista studentów:\n");
        for (Student student : students) {
            output.append(student.getInfo()).append("\n");
        }
        displayInfo(output.toString());
    }

    private void calculateAverage() {
        double average = manager.calculateAverageGrade();
        displayInfo("Średnia ocen: " + average);
    }

    private void displaySuccess(String message) {
        outputArea.setForeground(Color.GREEN);
        outputArea.setText(message);
    }

    private void displayError(String message) {
        outputArea.setForeground(Color.RED);
        outputArea.setText(message);
    }

    private void displayInfo(String message) {
        outputArea.setForeground(Color.BLUE);
        outputArea.setText(message);
    }

    private void cleanUpInputFields() {
        studentIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        gradeField.setText("");
    }
}
