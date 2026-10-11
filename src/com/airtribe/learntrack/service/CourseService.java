package com.airtribe.learntrack.service;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

/** Business logic for courses. */
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course addCourse(String name, String description, int durationInWeeks) {
        InputValidator.requireNotBlank(name, "Course name");
        InputValidator.requireNotBlank(description, "Description");
        if (durationInWeeks < AppConstants.MIN_COURSE_DURATION_WEEKS
                || durationInWeeks > AppConstants.MAX_COURSE_DURATION_WEEKS) {
            throw new InvalidInputException("Duration must be between "
                    + AppConstants.MIN_COURSE_DURATION_WEEKS + " and "
                    + AppConstants.MAX_COURSE_DURATION_WEEKS + " weeks.");
        }
        Course course = new Course(IdGenerator.getNextCourseId(),
                name.trim(), description.trim(), durationInWeeks);
        repository.save(course);
        return course;
    }

    public Course findCourseById(int id) {
        Course course = repository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course", id);
        }
        return course;
    }

    public List<Course> listCourses() {
        return repository.findAll();
    }

    public void activateCourse(int id) {
        findCourseById(id).setActive(true);
    }

    public void deactivateCourse(int id) {
        findCourseById(id).setActive(false);
    }
}
