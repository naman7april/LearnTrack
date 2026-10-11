package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

/** Business logic for students. */
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        InputValidator.requireNotBlank(firstName, "First name");
        InputValidator.requireNotBlank(lastName, "Last name");
        InputValidator.requireNotBlank(batch, "Batch");
        if (!InputValidator.isValidEmail(email)) {
            throw new InvalidInputException("Email address is not valid.");
        }
        Student student = new Student(IdGenerator.getNextStudentId(),
                firstName.trim(), lastName.trim(), email.trim(), batch.trim());
        repository.save(student);
        return student;
    }

    /** Overloaded version: add a student without an email. */
    public Student addStudent(String firstName, String lastName, String batch) {
        InputValidator.requireNotBlank(firstName, "First name");
        InputValidator.requireNotBlank(lastName, "Last name");
        InputValidator.requireNotBlank(batch, "Batch");
        Student student = new Student(IdGenerator.getNextStudentId(),
                firstName.trim(), lastName.trim(), batch.trim());
        repository.save(student);
        return student;
    }

    public Student findStudentById(int id) {
        Student student = repository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student", id);
        }
        return student;
    }

    public List<Student> listStudents() {
        return repository.findAll();
    }

    public void updateStudent(int id, String newBatch, String newEmail) {
        Student student = findStudentById(id);
        if (InputValidator.isNotBlank(newBatch)) {
            student.setBatch(newBatch.trim());
        }
        if (InputValidator.isNotBlank(newEmail)) {
            if (!InputValidator.isValidEmail(newEmail)) {
                throw new InvalidInputException("Email address is not valid.");
            }
            student.setEmail(newEmail.trim());
        }
    }

    public void deactivateStudent(int id) {
        findStudentById(id).setActive(false);
    }

    public void removeStudent(int id) {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Student", id);
        }
    }
}
