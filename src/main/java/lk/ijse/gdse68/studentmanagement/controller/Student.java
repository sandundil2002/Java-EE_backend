package lk.ijse.gdse68.studentmanagement.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.studentmanagement.dao.StudentDAOImpl;
import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;
import lk.ijse.gdse68.studentmanagement.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/student",loadOnStartup = +1)
public class Student extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(Student.class);
    Connection connection;
    public static String SAVE_STUDENT = "INSERT INTO student (id,name,email,city,level) VALUES(?,?,?,?,?)";
    public static String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    public static String UPDATE_STUDENT = "UPDATE student SET name=?,email=?,city=?,level=? WHERE id=?";
    public static String DELETE_STUDENT = "DELETE FROM student WHERE id=?";

    @Override
    public void init() {
        logger.info("Init method invoked");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/student_management_system");
            this.connection = pool.getConnection();
            logger.info("Connection initialized ", this.connection);
        } catch (SQLException | NamingException e) {
            logger.error("Initialize error");
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Todo:save student
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            //object binding of the json
            Jsonb jsonb = JsonbBuilder.create();
            var studentDAOImpl = new StudentDAOImpl();
            StudentDTO student = jsonb.fromJson(req.getReader(), StudentDTO.class);
            student.setId(Util.idGenerate());

            writer.write(studentDAOImpl.saveStudent(student,connection));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        //Todo:search student
        try (var writer = resp.getWriter()) {
            var studentDAOImpl = new StudentDAOImpl();
            Jsonb jsonb = JsonbBuilder.create();

            //Db process
            var studentId = req.getParameter("studentId");
            resp.setContentType("application/json");
            jsonb.toJson(studentDAOImpl.searchStudent(studentId,connection),writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        //Todo:update student
        try (var write = resp.getWriter()) {
            var studentDAOImpl = new StudentDAOImpl();
            var studentId = req.getParameter("studentId");
            Jsonb jsonb = JsonbBuilder.create();
            StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);

            if(studentDAOImpl.updateStudent(studentId,studentDTO,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        //Todo:delete student
        try (var writer = resp.getWriter()) {
            var studentId = req.getParameter("studentId");
            var studentDAOImpl = new StudentDAOImpl();

            if(studentDAOImpl.deleteStudent(studentId,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}