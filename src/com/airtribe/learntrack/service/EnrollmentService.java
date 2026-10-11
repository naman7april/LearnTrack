package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.List;

/** Business logic for enrollments. */
public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository repository,
                             StudentService studentService,
                             CourseService courseService) {
        this.repository = repository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId) {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!student.isActive()) {
            throw new InvalidInputException("Student " + studentId + " is inactive.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("Course " + courseId + " is inactive.");
        }
        if (hasActiveEnrollment(studentId, courseId)) {
            throw new InvalidInputException("Student is already actively enrolled in this course.");
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId, courseId);
        repository.save(enrollment);
        return enrollment;
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) {
        studentService.findStudentById(studentId); // throws if student does not exist
        return repository.findByStudentId(studentId);
    }

    public void markCompleted(int enrollmentId) {
        updateStatus(enrollmentId, EnrollmentStatus.COMPLETED);
    }

    public void markCancelled(int enrollmentId) {
        updateStatus(enrollmentId, EnrollmentStatus.CANCELLED);
    }

    private void updateStatus(int enrollmentId, EnrollmentStatus newStatus) {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("Enrollment is already " + enrollment.getStatus() + ".");
        }
        enrollment.setStatus(newStatus);
    }

    private Enrollment findEnrollmentById(int id) {
        Enrollment enrollment = repository.findById(id);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment", id);
        }
        return enrollment;
    }

    private boolean hasActiveEnrollment(int studentId, int courseId) {
        for (Enrollment e : repository.findByStudentId(studentId)) {
            if (e.getCourseId() == courseId && e.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }
}
