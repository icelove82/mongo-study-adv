package com.example.mongostudyadv;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(String id) {
        AtomicReference<Student> updated = new AtomicReference<>();
        studentRepository.findById(id).ifPresent(
                it -> {
                    it.setAddress(
                            Address.builder()
                                    .country(it.getAddress().getCountry())
                                    .city("Seoul")
                                    .postCode(it.getAddress().getPostCode())
                                    .build());
                    updated.set(studentRepository.save(it));
                }
        );

        return updated.get();

    }
}
