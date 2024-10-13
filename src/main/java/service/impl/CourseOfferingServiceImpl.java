package service.impl;

import dao.CourseOfferingsMapper;
import entity.CourseOffering;
import service.CourseOfferingService;
import utils.MybatisUtil;

import java.util.List;

public class CourseOfferingServiceImpl implements CourseOfferingService {
    @Override
    public List<CourseOffering> getAllCourseOfferings() {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper courseOfferingsMapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return courseOfferingsMapper.getAllCourseOfferings();
        });
    }

    @Override
    public boolean addCourseOffering(CourseOffering courseOffering) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.insertCourseOffering(courseOffering)>0;
        });
    }

    @Override
    public CourseOffering getCourseOffering(int id) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.getCourseOfferingsByOfferingId(id);
        });
    }

    @Override
    public boolean updateCourseOffering(CourseOffering courseOffering) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.updateCourseOffering(courseOffering)>0;
        });
    }

    @Override
    public List<CourseOffering> getCourseOfferingsByPage(int currentPage, int pageSize) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.selectCourseOfferingsByPage(pageSize, currentPage);
        });
    }

    @Override
    public List<CourseOffering> getUnselectedCoursesDetails(int currentPage, int pageSize, String studentNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.getUnselectedCoursesDetails(pageSize, currentPage, studentNo);
        });
    }

    @Override
    public List<CourseOffering> getSelectedCoursesDetails(int currentPage, int pageSize, String studentNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.getSelectedCoursesDetails(pageSize, currentPage, studentNo);
        });
    }

    @Override
    public List<CourseOffering> getTeacherCourses(int currentPage, int pageSize, String teacherNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.getTeacherCoursesInfo(pageSize,currentPage, teacherNo);
        });
    }

    @Override
    public int getCourseOfferingsCount() {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.countCourseOfferings();
        });
    }

    @Override
    public int getUnselectedCoursesCount(String studentNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.getUnselectedCoursesCount(studentNo);
        });
    }

    @Override
    public int getSelectedCoursesCount(String studentNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.getSelectedCoursesCount(studentNo);
        });
    }

    @Override
    public int getTeacherCoursesCount(String teacherNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.getTeacherCoursesCount(teacherNo);
        });
    }

    @Override
    public boolean deleteCourseOffering(int id) {
        return MybatisUtil.execute(sqlSession -> {
            CourseOfferingsMapper mapper = sqlSession.getMapper(CourseOfferingsMapper.class);
            return mapper.deleteCourseOffering(id)>0;
        });
    }
}
