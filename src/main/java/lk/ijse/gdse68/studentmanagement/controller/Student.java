package lk.ijse.gdse68.studentmanagement.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;
import lk.ijse.gdse68.studentmanagement.util.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/student")
public class Student extends HttpServlet {
    Connection connection;
    public static String SAVE_STUDENT = "INSERT INTO student (id,name,email,city,level) VALUES(?,?,?,?,?)";
    public static String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    @Override
    public void init() {
        try {
            var dbClass = getServletContext().getInitParameter("db-class");
            var dbUrl = getServletContext().getInitParameter("dburl");
            var dbUsername = getServletContext().getInitParameter("db-username");
            var dbPassword = getServletContext().getInitParameter("db-password");
            Class.forName(dbClass);
            this.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
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
            StudentDTO student = jsonb.fromJson(req.getReader(), StudentDTO.class);
            student.setId(Util.idGenerate());

            //save student
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getCity());
            ps.setString(5, student.getLevel());

            if (ps.executeUpdate() != 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.write("Student saved successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Failed to save student");
            }
        } catch (SQLException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        //Todo:search student
        try (var writer = resp.getWriter()) {
            var studentDTO = new StudentDTO();
            var studentId = req.getParameter("studentId");
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            while (ps.executeQuery().next()) {
                studentDTO.setId(ps.executeQuery().getString(1));
                studentDTO.setName(ps.executeQuery().getString(2));
                studentDTO.setEmail(ps.executeQuery().getString(3));
                studentDTO.setCity(ps.executeQuery().getString(4));
                studentDTO.setLevel(ps.executeQuery().getString(5));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        //Todo:update student
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        //Todo:delete student
    }
}