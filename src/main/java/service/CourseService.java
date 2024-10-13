package service;

import entity.Course;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    Course getCourseByNo(String courseNo);
    List<Course> getCoursesByType(String type,int currentPage, int pageSize);
    boolean addCourseWithObject(Course course);
    boolean updateCourseWithObject(Course course);
    boolean isCourseExist(String courseNo);
    List<Course> getCourseWithRowBounds(RowBounds rowBounds);
    List<Course> getAllCoursesWithPnoName(int currentPage, int pageSize);
    int getCoursesCount();
    int getCoursesCountByType(String Type);
    boolean deleteCourse(String courseNo);
}
