package com.example.nosqldemo.controller;

import com.example.nosqldemo.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> fetchStudents() {
        return studentService.getAllStudents();
    }
}
