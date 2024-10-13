package entity;

import lombok.Data;

import java.util.Date;
@Data
public class CourseOffering {
    private int offeringId;
    private String courseId;
    private String semester;
    private int year;
    private String instructorId;
    private int maxEnrollment;
    private int currentEnrollment;

    private String courseName;
    private String instructorName;

    public CourseOffering(String courseId, String semester, int year, String instructorId, int maxEnrollment, int currentEnrollment) {
        this.courseId = courseId;
        this.semester = semester;
        this.year = year;
        this.instructorId = instructorId;
        this.maxEnrollment = maxEnrollment;
        this.currentEnrollment = currentEnrollment;
    }

    public CourseOffering(String courseId, String semester, int year, String instructorId, int maxEnrollment, int currentEnrollment, String courseName, String instructorName) {
        this.courseId = courseId;
        this.semester = semester;
        this.year = year;
        this.instructorId = instructorId;
        this.maxEnrollment = maxEnrollment;
        this.currentEnrollment = currentEnrollment;
        this.courseName = courseName;
        this.instructorName = instructorName;
    }

    public CourseOffering() {}
}
