package service.impl;

import dao.CourseMapper;
import entity.Course;
import org.apache.ibatis.session.RowBounds;
import service.CourseService;
import utils.MybatisUtil;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    @Override
    public List<Course> getAllCourses() {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.selectAll();
        });
    }

    @Override
    public Course getCourseByNo(String courseNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.selectByNo(courseNo);
        });
    }

    @Override
    public List<Course> getCoursesByType(String type,int currentPage, int pageSize) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.getCoursesByType(type,pageSize,currentPage);
        });
    }

    @Override
    public boolean addCourseWithObject(Course course) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
           if (courseMapper.selectByNo(course.getNo()) == null) {
               return courseMapper.insertCourseWithObject(course)>0;
           }
            return false;
        });
    }

    @Override
    public boolean updateCourseWithObject(Course course) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            if (courseMapper.selectByNo(course.getNo()) == null) {
                return false;
            }
            return courseMapper.updateCourseWithObject(course)>0;
        });
    }

    @Override
    public boolean isCourseExist(String courseNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.selectByNo(courseNo)==null;
        });
    }

    @Override
    public List<Course> getCourseWithRowBounds(RowBounds rowBounds) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.selectCourseAllWithRowBounds(rowBounds);
        });
    }

    @Override
    public List<Course> getAllCoursesWithPnoName(int currentPage, int pageSize) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.getAllCoursesWithPnoName(pageSize,currentPage);
        });
    }

    @Override
    public int getCoursesCount() {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.countCourse();
        });
    }

    @Override
    public int getCoursesCountByType(String Type) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.countCourseByType(Type);
        });
    }

    @Override
    public boolean deleteCourse(String courseNo) {
        return MybatisUtil.execute(sqlSession -> {
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            return courseMapper.deleteCourseByNo(courseNo)>0;
        });
    }
}
