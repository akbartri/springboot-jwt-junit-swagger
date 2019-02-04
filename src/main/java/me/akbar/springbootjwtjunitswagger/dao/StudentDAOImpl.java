package me.akbar.springbootjwtjunitswagger.dao;

import me.akbar.springbootjwtjunitswagger.model.Student;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class StudentDAOImpl implements StudentDAO {

    @PersistenceContext
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Student> loadStudentByParams(Integer id, String name, String address, String email) {
        String query = " FROM Student s WHERE 1=1";

        if(id != null) {
            query += " AND s.id = " + id;
        }

        if(name != null && !name.trim().equals("")) {
            query += " AND s.name like '%" + name +"%'";
        }

        if(address != null && !address.trim().equals("")) {
            query += " AND s.address like '%" + address + "%'";
        }

        if(email != null && !email.trim().equals("")) {
            query += " AND s.email like '%" + email + "%'";
        }

        return (List<Student>) entityManager.createQuery(query).getResultList();
    }

    public String saveOrUpdateStudent(Student student) {
        boolean success = false;

        try{
            Student saveOrUpdate = new Student();

            if(!student.getName().trim().equals("") && !student.getName().equals(null)) {
                saveOrUpdate.setName(student.getName());
            }

            if(!student.getAddress().trim().equals("") && !student.getAddress().equals(null)) {
                saveOrUpdate.setAddress(student.getAddress());
            }

            if(!student.getEmail().trim().equals("") && !student.getEmail().equals(null)) {
                saveOrUpdate.setEmail(student.getEmail());
            }

            if (student.getId() == null) {
                entityManager.persist(saveOrUpdate);
            } else {
                saveOrUpdate.setId(student.getId());
                entityManager.flush();
            }

            success = true;
        } catch(HibernateException e) {
            System.out.println("Error when saving : "+ e.getMessage());
            success = false;
        }

        return (success) ? "SUCCESS": "ERROR";
    }

    public String deleteStudent(int id) {
        boolean success = false;

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(loadStudentById(id));
            success = true;
            entityManager.getTransaction().commit();
        } catch(HibernateException e) {
            System.out.println("Delete error: "+e.getMessage());
        }
        String result = (success) ? "SUCCESS": "ERROR";
        return result;
    }

    @Override
    public Student loadStudentById(int id) {
        return entityManager.find(Student.class, id);
    }
}