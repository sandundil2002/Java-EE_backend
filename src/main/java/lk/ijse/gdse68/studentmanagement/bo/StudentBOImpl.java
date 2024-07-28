package lk.ijse.gdse68.studentmanagement.bo;

import lk.ijse.gdse68.studentmanagement.dao.StudentDAOImpl;
import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;

import java.sql.Connection;

public class StudentBOImpl implements StudentBO{
    @Override
    public String saveStudent(StudentDTO student, Connection connection) {
        var studentDAOImpl = new StudentDAOImpl();
        return studentDAOImpl.saveStudent(student,connection);
    }

    @Override
    public boolean updateStudent(String id, StudentDTO student, Connection connection) {
        var studentDAOImpl = new StudentDAOImpl();
        return studentDAOImpl.updateStudent(id,student,connection);
    }

    @Override
    public StudentDTO searchStudent(String id, Connection connection) {
        var studentDAOImpl = new StudentDAOImpl();
        return studentDAOImpl.searchStudent(id,connection);
    }

    @Override
    public boolean deleteStudent(String id, Connection connection) {
        var studentDAOImpl = new StudentDAOImpl();
        return studentDAOImpl.deleteStudent(id,connection);
    }
}
