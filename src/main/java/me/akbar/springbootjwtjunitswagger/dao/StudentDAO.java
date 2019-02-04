package me.akbar.springbootjwtjunitswagger.dao;

import me.akbar.springbootjwtjunitswagger.model.Student;

import java.util.List;

public interface StudentDAO {
    public List<Student> loadStudentByParams(Integer id, String name, String address, String email);
    public String saveOrUpdateStudent(Student student);
    public String deleteStudent(int id);
    public Student loadStudentById(int id);
}