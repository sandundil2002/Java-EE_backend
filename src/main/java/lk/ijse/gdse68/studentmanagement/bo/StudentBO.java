package lk.ijse.gdse68.studentmanagement.bo;

import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;

import java.sql.Connection;

public interface StudentBO {
    String saveStudent(StudentDTO student, Connection connection);
    boolean updateStudent(String id, StudentDTO student, Connection connection);
    StudentDTO searchStudent(String id, Connection connection);
    boolean deleteStudent(String id, Connection connection);
}
