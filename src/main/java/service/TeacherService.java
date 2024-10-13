package service;

import entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getAllTeachers();
    List<Teacher> getTeacherByPage(int currentPage, int pageSize);
    Teacher getTeacherById(String teacherNo);
    Teacher loginTeacher(String teacherNo, String teacherPassword);
    boolean addTeacherWithObject(Teacher teacher);
    boolean updateTeacherWithObject(Teacher teacher);
    int getTeacherCount();
    boolean deleteTeacher(String teacherNo);
}
