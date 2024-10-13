package service.impl;

import dao.EnrollmentMapper;
import entity.CourseOffering;
import entity.Enrollment;
import entity.Student;
import service.EnrollmentService;
import utils.MybatisUtil;

import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {

    @Override
    public List<Enrollment> getAllEnrollments() {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.getAllEnrollments();
        });
    }

    @Override
    public boolean addEnrollment(Enrollment enrollment) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.insertEnrollment(enrollment)>0;
        });
    }

    @Override
    public boolean updateEnrollment(Enrollment enrollment) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.updateEnrollment(enrollment)>0;
        });
    }

    @Override
    public List<Enrollment> getEnrollmentsByOfferingId(int offeringId, int currentPage, int pageSize) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.getAllEnrollmentsByOfferingId(offeringId, pageSize, currentPage);
        });
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentId(int currentPage, int pageSize, String studentId) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.getAllEnrollmentsByStudentId(studentId, currentPage, pageSize);
        });
    }

    @Override
    public int getEnrollmentCountByStudentId(String studentId) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.countEnrollmentByStudentId(studentId);
        });
    }

    @Override
    public int getEnrollmentCountByOfferingId(int offeringId) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.countEnrollmentByOfferingId(offeringId);
        });
    }

    @Override
    public Enrollment getEnrollmentByStudentIdAndOfferingId(Student student, CourseOffering courseOffering) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.getEnrollmentByStudentIdAndOfferingId(student.getNo(),courseOffering.getOfferingId());
        });
    }

    @Override
    public boolean deleteEnrollment(Enrollment enrollment) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.deleteEnrollmentById(enrollment.getEnrollmentId())>0;
        });
    }

    @Override
    public Enrollment getEnrollmentById(int id) {
        return MybatisUtil.execute(sqlSession -> {
            EnrollmentMapper mapper = sqlSession.getMapper(EnrollmentMapper.class);
            return mapper.getTeacherCourseGrade(id);
        });
    }
}
