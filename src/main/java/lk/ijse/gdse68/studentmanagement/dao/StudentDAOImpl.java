package lk.ijse.gdse68.studentmanagement.dao;

import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class StudentDAOImpl implements StudentDAO{
    public static String SAVE_STUDENT = "INSERT INTO student (id,name,email,city,level) VALUES(?,?,?,?,?)";
    public static String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    public static String UPDATE_STUDENT = "UPDATE student SET name=?,email=?,city=?,level=? WHERE id=?";
    public static String DELETE_STUDENT = "DELETE FROM student WHERE id=?";

    @Override
    public String saveStudent(StudentDTO student, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getCity());
            ps.setString(5, student.getLevel());

            if (ps.executeUpdate() != 0) {
                return "Student saved";
            } else {
                return "Student not saved";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public StudentDTO updateStudent(String id, StudentDTO student, Connection connection) {
        return null;
    }

    @Override
    public StudentDTO searchStudent(String id, Connection connection) {
        try {
            StudentDTO studentDTO = new StudentDTO();
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, id);
            var rst = ps.executeQuery();

            while (rst.next()) {
                studentDTO.setId(rst.getString("id"));
                studentDTO.setName(rst.getString("name"));
                studentDTO.setEmail(rst.getString("email"));
                studentDTO.setCity(rst.getString("city"));
                studentDTO.setLevel(rst.getString("level"));
            }
            return studentDTO;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteStudent(String id, Connection connection) {
        return false;
    }
}
