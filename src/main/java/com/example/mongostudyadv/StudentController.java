package com.example.mongostudyadv;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("study/mongodb/student")
@AllArgsConstructor
public class StudentController {

    static final String STUDENT_ID = "STUDENT_ID";

    private final StudentService studentService;

    @GetMapping("all")
    public List<Student> fetchAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("by/id")
    public Student updateStudent(@RequestParam(STUDENT_ID) String id) {
        return studentService.updateStudent(id);
    }

}
