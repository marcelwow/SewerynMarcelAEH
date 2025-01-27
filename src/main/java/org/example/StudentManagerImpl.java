package org.example;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {
    public StudentManagerImpl(){
        try(StudentTable studentTable = new StudentTable())
        {
            studentTable.createTableIfNotExists();
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void addStudent(Student student){
        try(StudentTable studentTable = new StudentTable())
        {
            studentTable.addStudent(student);
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void removeStudent(int studentId) {
        try(StudentTable studentTable = new StudentTable())
        {
            studentTable.removeStudent(studentId);
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void updateStudent(Student student) {
        try(StudentTable studentTable = new StudentTable())
        {
            studentTable.updateStudent(student);
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public Student getStudent(int studentId) {
        try(StudentTable studentTable = new StudentTable())
        {
            return studentTable.getStudent(studentId);
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }
        return null;
    }

    @Override
    public ArrayList<Student> getAllStudents() {
        try(StudentTable studentTable = new StudentTable())
        {
            return studentTable.getStudents();
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }
        return new ArrayList<Student>();
    }

    @Override
    public double calculateAverageGrade() {
        ArrayList<Student> students = this.getAllStudents();
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
