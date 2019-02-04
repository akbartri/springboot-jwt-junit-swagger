package me.akbar.springbootjwtjunitswagger.service;

import me.akbar.springbootjwtjunitswagger.dao.StudentDAO;
import me.akbar.springbootjwtjunitswagger.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDAO dao;

    @Override
    public List<Student> loadStudentByParams(Integer id, String name, String address, String email) {
        return dao.loadStudentByParams(id, name, address, email);
    }

    @Override
    public String saveOrUpdateStudent(Student student) {
        return dao.saveOrUpdateStudent(student);
    }

    @Override
    public String deleteStudent(int id) {
        return dao.deleteStudent(id);
    }

    @Override
    public Student loadStudentById(int id) {
        return dao.loadStudentById(id);
    }
}
