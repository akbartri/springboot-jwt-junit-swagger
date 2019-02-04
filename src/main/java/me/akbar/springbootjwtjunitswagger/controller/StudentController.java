package me.akbar.springbootjwtjunitswagger.controller;

import me.akbar.springbootjwtjunitswagger.model.Student;
import me.akbar.springbootjwtjunitswagger.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService service;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> saveStudent(@RequestBody Student student, UriComponentsBuilder builder){
        String flag = service.saveOrUpdateStudent(student);
        if(flag.equals("ERROR")){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Student>> loadStudentByParams(@RequestBody Student student, HttpServletResponse response){
        List<Student> datas = service.loadStudentByParams(student.getId(), student.getName(), student.getAddress(), student.getEmail());

        return new ResponseEntity<List<Student>>(datas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Student> loadStudentById(@PathVariable("id")Integer id){

        Student student = service.loadStudentById(id);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        if(service.loadStudentById(student.getId()) == null) {
            return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
        }
        String result = service.saveOrUpdateStudent(student);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteStudent(@PathVariable(value="id")Integer id){
        String result = service.deleteStudent(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
