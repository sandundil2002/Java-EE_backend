package lk.ijse.gdse68.studentmanagement.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = "/student")
public class Student extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Todo:save student
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(s -> sb.append(s).append("\n"));
        System.out.println(sb);
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