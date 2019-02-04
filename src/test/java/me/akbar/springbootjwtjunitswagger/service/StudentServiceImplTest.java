package me.akbar.springbootjwtjunitswagger.service;

import me.akbar.springbootjwtjunitswagger.dao.StudentDAOImpl;
import me.akbar.springbootjwtjunitswagger.model.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StudentServiceImplTest {
    @InjectMocks
    StudentServiceImpl service;

    @Mock
    StudentDAOImpl dao;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadStudentByIdTest() {
        Student data = new Student(2,"name2","address2","email2@email2.com");

        when(dao.loadStudentById(2)).thenReturn(data);
        Student result = service.loadStudentById(2);
        Assert.assertEquals("name2",result.getName());
    }

    @Test
    public void loadStudentByParamsTest() {
        List<Student> datas = new ArrayList<>();
        for (int idx = 2; idx<5; idx++) {
            Student data = new Student(idx,"name"+idx,"address"+idx,"email"+idx+"@email"+idx+".com");

            datas.add(data);
        }

        when(dao.loadStudentByParams(null,null,null,null)).thenReturn(datas);
        List<Student> result = service.loadStudentByParams(null,null,null,null);
        Assert.assertEquals("name2",result.get(0).getName());
        Assert.assertEquals(3,result.size());
    }

    @Test
    public void saveOrUpdateStudentTest() {
        Student data = new Student(2,"name2","address2","email2@email2.com");

        when(dao.saveOrUpdateStudent(data)).thenReturn("success");
        String result = service.saveOrUpdateStudent(data);
        Assert.assertEquals("success",result);
    }

    @Test
    public void deleteStudentTest() {
        Student data = new Student(2,"name2","address2","email2@email2.com");
        service.deleteStudent(2);
        verify(dao, times(1)).deleteStudent(2);
    }
}