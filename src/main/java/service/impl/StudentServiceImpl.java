package service.impl;

import dao.StudentMapper;
import entity.Student;
import org.apache.ibatis.session.RowBounds;
import service.StudentService;
import utils.MybatisUtil;

import java.util.List;

public class StudentServiceImpl implements StudentService {


    @Override
    public boolean addStudentWithObject(Student student) {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            if(studentMapper.isStudentExistByNo(student.getNo())!=null)
            {
                return false;
            }
            return studentMapper.insertStudentWithObject(student)>0;
        });
    }

    @Override
    public boolean deleteStudentByNo(Student student) {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.deleteStudentByNo(student.getNo())>0;
        });
    }

    @Override
    public Student getStudentByNo(String studentNo) {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.selectStudentByNo(studentNo);
        });
    }

    @Override
    public int getStudentsCountByDept(String dept) {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.countStudentsByDept(dept);
        });
    }

    @Override
    public int getStudentsCount() {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.countStudents();
        });
    }

    @Override
    public List<Student> getStudentsByDeptAndPage(String dept, int currentPage, int pageSize) {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.selectStudentsByDeptAndPage(dept, pageSize, currentPage);
        });
    }

    @Override
    public Student login(String studentNo, String password) {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.selectStudentByNoAndPassword(studentNo, password);
        });
    }

    @Override
    public boolean upStudentByNo(Student student) {
        return MybatisUtil.execute(sqlSession -> {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            if(studentMapper.isStudentExistByNo(student.getNo())==null){
                return false;
            }
            return studentMapper.updateStudentWithObject(student)>0;
        });
    }
}
