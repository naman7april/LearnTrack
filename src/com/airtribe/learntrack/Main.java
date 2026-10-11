package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

/** Console entry point: shows menus, reads input, calls services. */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentService studentService = new StudentService(new StudentRepository());
    private static final CourseService courseService = new CourseService(new CourseRepository());
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(new EnrollmentRepository(), studentService, courseService);

    public static void main(String[] args) {
        System.out.println("=== " + AppConstants.APP_NAME + " v" + AppConstants.APP_VERSION + " ===");
        boolean running = true;
        while (running) {
            printMainMenu();
            try {
                int choice = readInt("Choose an option: ");
                switch (choice) {
                    case MenuOptions.STUDENT_MENU:
                        studentMenu();
                        break;
                    case MenuOptions.COURSE_MENU:
                        courseMenu();
                        break;
                    case MenuOptions.ENROLLMENT_MENU:
                        enrollmentMenu();
                        break;
                    case MenuOptions.EXIT:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------- menus ----------

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("0. Exit");
    }

    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Deactivate student");
            System.out.println("5. Update student (batch / email)");
            System.out.println("0. Back");
            try {
                switch (readInt("Choose an option: ")) {
                    case MenuOptions.ADD_STUDENT: addStudent(); break;
                    case MenuOptions.VIEW_STUDENTS: viewStudents(); break;
                    case MenuOptions.SEARCH_STUDENT: searchStudent(); break;
                    case MenuOptions.DEACTIVATE_STUDENT: deactivateStudent(); break;
                    case MenuOptions.UPDATE_STUDENT: updateStudent(); break;
                    case MenuOptions.BACK: back = true; break;
                    default: System.out.println("Invalid option, please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate course");
            System.out.println("4. Deactivate course");
            System.out.println("0. Back");
            try {
                switch (readInt("Choose an option: ")) {
                    case MenuOptions.ADD_COURSE: addCourse(); break;
                    case MenuOptions.VIEW_COURSES: viewCourses(); break;
                    case MenuOptions.ACTIVATE_COURSE:
                        courseService.activateCourse(readInt("Course ID: "));
                        System.out.println("Course activated.");
                        break;
                    case MenuOptions.DEACTIVATE_COURSE:
                        courseService.deactivateCourse(readInt("Course ID: "));
                        System.out.println("Course deactivated.");
                        break;
                    case MenuOptions.BACK: back = true; break;
                    default: System.out.println("Invalid option, please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll student in course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. Mark enrollment completed");
            System.out.println("4. Mark enrollment cancelled");
            System.out.println("0. Back");
            try {
                switch (readInt("Choose an option: ")) {
                    case MenuOptions.ENROLL_STUDENT: enrollStudent(); break;
                    case MenuOptions.VIEW_STUDENT_ENROLLMENTS: viewEnrollments(); break;
                    case MenuOptions.MARK_COMPLETED:
                        enrollmentService.markCompleted(readInt("Enrollment ID: "));
                        System.out.println("Enrollment marked COMPLETED.");
                        break;
                    case MenuOptions.MARK_CANCELLED:
                        enrollmentService.markCancelled(readInt("Enrollment ID: "));
                        System.out.println("Enrollment marked CANCELLED.");
                        break;
                    case MenuOptions.BACK: back = true; break;
                    default: System.out.println("Invalid option, please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------- student actions ----------

    private static void addStudent() {
        String first = readLine("First name: ");
        String last = readLine("Last name: ");
        String email = readLine("Email (leave blank to skip): ");
        String batch = readLine("Batch: ");
        Student student = InputValidator.isNotBlank(email)
                ? studentService.addStudent(first, last, email, batch)
                : studentService.addStudent(first, last, batch);
        System.out.println("Added: " + student);
    }

    private static void viewStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudent() {
        Student student = studentService.findStudentById(readInt("Student ID: "));
        System.out.println(student.getDisplayName());
        System.out.println(student);
    }

    private static void deactivateStudent() {
        studentService.deactivateStudent(readInt("Student ID: "));
        System.out.println("Student deactivated.");
    }

    private static void updateStudent() {
        int id = readInt("Student ID: ");
        String batch = readLine("New batch (blank to keep): ");
        String email = readLine("New email (blank to keep): ");
        studentService.updateStudent(id, batch, email);
        System.out.println("Student updated.");
    }

    // ---------- course actions ----------

    private static void addCourse() {
        String name = readLine("Course name: ");
        String description = readLine("Description: ");
        int weeks = readInt("Duration in weeks: ");
        System.out.println("Added: " + courseService.addCourse(name, description, weeks));
    }

    private static void viewCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses yet.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    // ---------- enrollment actions ----------

    private static void enrollStudent() {
        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");
        System.out.println("Enrolled: " + enrollmentService.enrollStudent(studentId, courseId));
    }

    private static void viewEnrollments() {
        List<Enrollment> list = enrollmentService.getEnrollmentsForStudent(readInt("Student ID: "));
        if (list.isEmpty()) {
            System.out.println("No enrollments for this student.");
            return;
        }
        for (Enrollment enrollment : list) {
            System.out.println(enrollment);
        }
    }

    // ---------- input helpers ----------

    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput closed. Exiting.");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    private static int readInt(String prompt) {
        return InputValidator.parseInt(readLine(prompt), "Input");
    }
}
