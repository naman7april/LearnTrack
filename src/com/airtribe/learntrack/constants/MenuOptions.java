package com.airtribe.learntrack.constants;

/** Numeric codes for the menus shown by Main. */
public final class MenuOptions {

    // Main menu
    public static final int STUDENT_MENU = 1;
    public static final int COURSE_MENU = 2;
    public static final int ENROLLMENT_MENU = 3;
    public static final int EXIT = 0;

    // Student menu
    public static final int ADD_STUDENT = 1;
    public static final int VIEW_STUDENTS = 2;
    public static final int SEARCH_STUDENT = 3;
    public static final int DEACTIVATE_STUDENT = 4;
    public static final int UPDATE_STUDENT = 5;

    // Course menu
    public static final int ADD_COURSE = 1;
    public static final int VIEW_COURSES = 2;
    public static final int ACTIVATE_COURSE = 3;
    public static final int DEACTIVATE_COURSE = 4;

    // Enrollment menu
    public static final int ENROLL_STUDENT = 1;
    public static final int VIEW_STUDENT_ENROLLMENTS = 2;
    public static final int MARK_COMPLETED = 3;
    public static final int MARK_CANCELLED = 4;

    public static final int BACK = 0;

    private MenuOptions() {
    }
}
