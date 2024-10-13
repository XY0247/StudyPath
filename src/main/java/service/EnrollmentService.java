package service;

import entity.CourseOffering;
import entity.Enrollment;
import entity.Student;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();
    boolean addEnrollment(Enrollment enrollment);
    boolean updateEnrollment(Enrollment enrollment);
    List<Enrollment> getEnrollmentsByOfferingId(int offeringId, int currentPage, int pageSize);
    int getEnrollmentCountByOfferingId(int offeringId);
    List<Enrollment> getEnrollmentsByStudentId(int currentPage, int pageSize, String studentId);
    int getEnrollmentCountByStudentId(String studentId);
    Enrollment getEnrollmentByStudentIdAndOfferingId(Student student, CourseOffering courseOffering);
    boolean deleteEnrollment(Enrollment enrollment);
    Enrollment getEnrollmentById(int id);
}
