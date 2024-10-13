package dao;
import entity.CourseOffering;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseOfferingsMapper {
    // 定义 ResultMap
    @Results(id = "courseOfferingResult", value = {
            @Result(column = "offering_id", property = "offeringId"),
            @Result(column = "course_id", property = "courseId"),
            @Result(column = "semester", property = "semester"),
            @Result(column = "year", property = "year"),
            @Result(column = "instructor_id", property = "instructorId"),
            @Result(column = "max_enrollment", property = "maxEnrollment"),
            @Result(column = "current_enrollment", property = "currentEnrollment"),
            @Result(column = "course_name", property = "courseName"),  // 映射课程名称
            @Result(column = "instructor_name", property = "instructorName")  // 映射教师名称
    })
    @Select("""
        SELECT
            offering_id,
            course_id,
            semester,
            year,
            instructor_id,
            max_enrollment,
            current_enrollment,
            t.name AS instructor_name,      -- 教师名称别名
            courses.name AS course_name     -- 课程名称别名
        FROM course_offerings c
        LEFT JOIN teacher t ON c.instructor_id = t.no
        JOIN course courses ON c.course_id = courses.no
    """)
    List<CourseOffering> getAllCourseOfferings();

    // 使用 ResultMap 查询所有课程开设信息
    @Select("""
        SELECT
            c.offering_id,
            c.course_id,
            c.semester,
            c.year,
            c.instructor_id,
            c.max_enrollment,
            c.current_enrollment,
            t.name AS instructor_name,      -- 教师名称别名
            courses.name AS course_name     -- 课程名称别名
        FROM course_offerings c
        LEFT JOIN teacher t ON c.instructor_id = t.no
        JOIN course courses ON c.course_id = courses.no
        WHERE offering_id = #{offeringId}
    """)
    @ResultMap("courseOfferingResult")
    CourseOffering getCourseOfferingsByOfferingId(@Param("offeringId") int offeringId);
    // 插入一条 CourseOffering 记录
    @Insert("""
        INSERT INTO Course_Offerings (
            course_id,
            semester,
            year,
            instructor_id,
            max_enrollment,
            current_enrollment
        ) VALUES (
            #{courseId},
            #{semester},
            #{year},
            #{instructorId},
            #{maxEnrollment},
            #{currentEnrollment}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "offeringId", keyColumn = "offering_id") // 自动生成主键
    int insertCourseOffering(CourseOffering courseOffering);

    @Update("""
    UPDATE course_offerings
    SET
        course_id = #{courseId},
        semester = #{semester},
        year = #{year},
        instructor_id = #{instructorId},
        max_enrollment = #{maxEnrollment},
        current_enrollment = #{currentEnrollment}
    WHERE offering_id = #{offeringId}
""")
    int updateCourseOffering(CourseOffering courseOffering);


    @ResultMap("courseOfferingResult")
    @Select("""
        SELECT
            offering_id,
            course_id,
            semester,
            year,
            instructor_id,
            max_enrollment,
            current_enrollment,
            t.name AS instructor_name,      -- 教师名称别名
            courses.name AS course_name     -- 课程名称别名
        FROM course_offerings c
        LEFT JOIN teacher t ON c.instructor_id = t.no
        JOIN course courses ON c.course_id = courses.no
        LIMIT #{pageSize} OFFSET #{offset}
    """)
    List<CourseOffering> selectCourseOfferingsByPage(@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("select count(*) from course_offerings")
    int countCourseOfferings();

    @ResultMap("courseOfferingResult")
    @Select("""
        SELECT
            c.offering_id,
            c.course_id,
            c.semester,
            c.year,
            c.instructor_id,
            c.max_enrollment,
            c.current_enrollment,
            t.name AS instructor_name,      -- 教师名称别名
            courses.name AS course_name     -- 课程名称别名
        FROM course_offerings c
                 LEFT JOIN teacher t ON c.instructor_id = t.no
                 JOIN course courses ON c.course_id = courses.no
                 LEFT JOIN enrollments enrollment ON c.offering_id = enrollment.offering_id AND enrollment.student_id = #{studentId}
        WHERE enrollment.student_id IS NULL
        LIMIT #{pageSize} OFFSET #{offset}
    """)
    List<CourseOffering> getUnselectedCoursesDetails(@Param("pageSize") int pageSize, @Param("offset") int offset , @Param("studentId") String studentId);

    @Select("""
    SELECT COUNT(*)
    FROM course_offerings c
    LEFT JOIN enrollments e ON c.offering_id = e.offering_id AND e.student_id = #{studentId}
    WHERE e.student_id IS NULL;
""")
    int getUnselectedCoursesCount(@Param("studentId") String studentId);

    @ResultMap("courseOfferingResult")
    @Select("""
        SELECT
            c.offering_id,
            c.course_id,
            c.semester,
            c.year,
            c.instructor_id,
            c.max_enrollment,
            c.current_enrollment,
            t.name AS instructor_name,      -- 教师名称别名
            courses.name AS course_name     -- 课程名称别名
        FROM course_offerings c
                 LEFT JOIN teacher t ON c.instructor_id = t.no
                 JOIN course courses ON c.course_id = courses.no
                 LEFT JOIN enrollments enrollment ON c.offering_id = enrollment.offering_id AND enrollment.student_id = #{studentId}
        WHERE enrollment.student_id IS NOT NULL
        LIMIT #{pageSize} OFFSET #{offset}
    """)
    //获得已选择的课
    List<CourseOffering> getSelectedCoursesDetails(@Param("pageSize") int pageSize, @Param("offset") int offset , @Param("studentId") String studentId);
    @Select("""
    SELECT COUNT(*)
    FROM course_offerings c
    LEFT JOIN enrollments e ON c.offering_id = e.offering_id AND e.student_id = #{studentId}
    WHERE e.student_id IS NOT NULL
""")
    int getSelectedCoursesCount(@Param("studentId") String studentId);


    @ResultMap("courseOfferingResult")
    @Select("""
        SELECT
            offering_id,
            course_id,
            semester,
            year,
            instructor_id,
            max_enrollment,
            current_enrollment,
            t.name AS instructor_name,      -- 教师名称别名
            courses.name AS course_name     -- 课程名称别名
        FROM course_offerings c
        LEFT JOIN teacher t ON c.instructor_id = t.no
        JOIN course courses ON c.course_id = courses.no
        WHERE instructor_id = #{teacherNo}
        LIMIT #{pageSize} OFFSET #{offset}
    """)//pageSize = 每页显示多少条数据 offset = 从第几条数据开始获取
    List<CourseOffering> getTeacherCoursesInfo(@Param("pageSize") int pageSize, @Param("offset") int offset , @Param("teacherNo") String teacherNo);

    @Select("select count(*) from course_offerings where instructor_id = #{teacherNo}")
    int getTeacherCoursesCount(@Param("teacherNo") String teacherNo);

    @Delete("delete from course_offerings where offering_id = #{offeringId}")
    int deleteCourseOffering(@Param("offeringId") int offeringId);
}
