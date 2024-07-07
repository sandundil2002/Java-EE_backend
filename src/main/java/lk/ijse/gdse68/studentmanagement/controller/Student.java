package lk.ijse.gdse68.studentmanagement.controller;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;
import lk.ijse.gdse68.studentmanagement.util.Util;

import java.io.IOException;

@WebServlet(urlPatterns = "/student")
public class Student extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Todo:save student
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        //object binding of the json
        Jsonb jsonb = JsonbBuilder.create();
        StudentDTO student = jsonb.fromJson(req.getReader(), StudentDTO.class);
        student.setId(Util.idGenerate());
        System.out.println("Student ID: " + student.getId());
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Email: " + student.getEmail());
        System.out.println("Student Level: " + student.getLevel());
        resp.setContentType("application/json");
        jsonb.toJson(student, resp.getWriter());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        //Todo:search student
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