package org.example;

import java.sql.*;
import java.util.ArrayList;

public class StudentTable implements AutoCloseable {
    private Connection connection;

    public StudentTable() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        this.createTableIfNotExists();
    }

    public void createTableIfNotExists() throws SQLException {
        String sql = """
                    CREATE TABLE IF NOT EXISTS Student (
                       studentId INTEGER PRIMARY KEY AUTOINCREMENT,
                       firstName TEXT NOT NULL,
                       lastName TEXT NOT NULL,
                       age INTEGER NOT NULL,
                       grade REAL
                    );""";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Student (firstName, lastName, age, grade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setDouble(4, student.getGrade());
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println("Dodano studenta o id: " + rs.getInt(1));
                }
            }
        }
    }

    public void updateStudent(Student student) throws SQLException {
        String sql = """
                UPDATE Student
                SET firstName = ?,
                lastName = ?,
                age = ?,
                grade = ?
                WHERE studentId = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setDouble(4, student.getGrade());
            preparedStatement.setInt(5, student.getStudentId());
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                System.out.println("Zaktualizowano dane studenta o id: " + student.getStudentId());
            }
        }
    }

    public void removeStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM Student WHERE studentId = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, studentId);
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                System.out.println("Usunieto studenta o id: " + studentId);
            }
        }
    }

    public Student getStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM Student WHERE studentId = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);)
        {
            preparedStatement.setInt(1, studentId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("studentId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("age"),
                            rs.getDouble("grade")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Student> getStudents() throws SQLException {
        String sql = "SELECT * FROM Student ORDER BY studentId asc;";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery()
        )
        {

            ArrayList<Student> list = new ArrayList<>();
            while (rs.next())
            {
                Student student = new Student(
                        rs.getInt("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("age"),
                        rs.getDouble("grade")
                        );
                list.add(student);
            }
            return list;
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}