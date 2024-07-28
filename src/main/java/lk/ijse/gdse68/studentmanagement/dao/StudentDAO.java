package lk.ijse.gdse68.studentmanagement.dao;

import lk.ijse.gdse68.studentmanagement.controller.Student;
import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;

import java.sql.Connection;

public sealed interface StudentDAO permits StudentDAOImpl {
    String saveStudent(StudentDTO student, Connection connection);
    boolean updateStudent(String id, StudentDTO student, Connection connection);
    StudentDTO searchStudent(String id, Connection connection);
    boolean deleteStudent(String id, Connection connection);
}
