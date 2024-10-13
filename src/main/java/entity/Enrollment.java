package entity;

import lombok.Data;

import java.util.Date;
@Data
public class Enrollment {
    private int enrollmentId;
    private int offeringId;
    private String studentId;
    private String status;
    private int grade;

    private String courseName;//课程名
    private String studentName;//学生名
    private String instructorName;//教师名
    private String instructorId;//教师id
    private String studentDept;//学生学院
    public Enrollment(String studentId, int offeringId,String status, int grade, String courseName, String studentName, String instructorName, String instructorId,String studentDept) {
        this.studentId = studentId;
        this.offeringId = offeringId;
        this.status = status;
        this.grade = grade;
        this.courseName = courseName;
        this.studentName = studentName;
        this.instructorName = instructorName;
        this.instructorId = instructorId;
        this.studentDept = studentDept;
    }

    public Enrollment(String studentId, int offeringId,  String status, int grade) {
        this.studentId = studentId;
        this.offeringId = offeringId;
        this.status = status;
        this.grade = grade;
    }

    public Enrollment() {}

}
