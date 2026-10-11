package com.airtribe.learntrack.util;

/** Hands out unique IDs. Static counters are shared across the whole application. */
public class IdGenerator {

    private static int studentIdCounter = 1;
    private static int courseIdCounter = 1;
    private static int enrollmentIdCounter = 1;
    private static int trainerIdCounter = 1;

    private IdGenerator() {
    }

    public static int getNextStudentId() {
        return studentIdCounter++;
    }

    public static int getNextCourseId() {
        return courseIdCounter++;
    }

    public static int getNextEnrollmentId() {
        return enrollmentIdCounter++;
    }

    public static int getNextTrainerId() {
        return trainerIdCounter++;
    }
}
