package service;

import entity.CourseOffering;

import java.util.List;

public interface CourseOfferingService {
    List<CourseOffering> getAllCourseOfferings();
    boolean addCourseOffering(CourseOffering courseOffering);
    CourseOffering getCourseOffering(int id);
    boolean updateCourseOffering(CourseOffering courseOffering);
    List<CourseOffering> getCourseOfferingsByPage(int currentPage, int pageSize);
    List<CourseOffering> getUnselectedCoursesDetails(int currentPage, int pageSize, String studentNo);
    List<CourseOffering> getSelectedCoursesDetails(int currentPage, int pageSize, String studentNo);
    List<CourseOffering> getTeacherCourses(int currentPage, int pageSize, String teacherNo);
    int getCourseOfferingsCount();
    int getUnselectedCoursesCount(String studentNo);
    int getSelectedCoursesCount(String studentNo);
    int getTeacherCoursesCount(String teacherNo);
    boolean deleteCourseOffering(int id);
}
