package lk.ijse.gdse68.studentmanagement.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.studentmanagement.dto.StudentDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/demo")
public class Demo extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        var initParameter = config.getInitParameter("db");
        System.out.println("DB Connection: " + initParameter);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        List<StudentDTO> studentDTO = jsonb.fromJson(req.getReader(),
                new ArrayList<StudentDTO>(){}.getClass().getGenericSuperclass());
        studentDTO.forEach(System.out::println);
    }
}
