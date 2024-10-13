package service;

import entity.Student;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface StudentService {
    Student getStudentByNo(String studentNo);
    Student login(String studentNo, String password);
    List<Student> getStudentsByDeptAndPage(String dept, int currentPage, int pageSize);
    int getStudentsCountByDept(String dept);
    int getStudentsCount();
    boolean upStudentByNo(Student student);
    boolean addStudentWithObject(Student student);
    boolean deleteStudentByNo(Student student);
}
