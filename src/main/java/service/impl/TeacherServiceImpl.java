package service.impl;

import dao.TeacherMapper;
import entity.Course;
import entity.Teacher;
import service.TeacherService;
import utils.MybatisUtil;
import utils.PageResult;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    @Override
    public List<Teacher> getAllTeachers() {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            return teacherMapper.selectAll();
        });
    }

    @Override
    public List<Teacher> getTeacherByPage(int currentPage, int pageSize) {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            return teacherMapper.selectTeachersByPage(pageSize,currentPage);
        });
    }

    @Override
    public Teacher getTeacherById(String teacherNo) {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            return teacherMapper.selectTeacherByNo(teacherNo);
        });
    }

    @Override
    public Teacher loginTeacher(String teacherNo, String teacherPassword) {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            return teacherMapper.selectTeacherByNoAndPassword(teacherNo, teacherPassword);
        });
    }

    @Override
    public boolean addTeacherWithObject(Teacher teacher) {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            if (teacherMapper.selectTeacherByNo(teacher.getNo()) == null) {
                return teacherMapper.insertTeacherWithObject(teacher)>0;
            }
            return false;
        });
    }

    @Override
    public boolean updateTeacherWithObject(Teacher teacher) {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            if (teacherMapper.selectTeacherByNo(teacher.getNo()) != null) {
                return teacherMapper.updateTeacherWithObject(teacher)>0;
            }
            return false;
        });
    }

    @Override
    public int getTeacherCount() {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            return teacherMapper.countTeacher();
        });
    }

    @Override
    public boolean deleteTeacher(String teacherNo) {
        return MybatisUtil.execute(sqlSession -> {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            return teacherMapper.deleteTeacherByNo(teacherNo)>0;
        });
    }

}
