package dao;

import entity.Enrollment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EnrollmentMapper {
    @Select("""
    SELECT
        e.enrollment_id,
        e.student_id,
        e.offering_id,
        e.status,
        e.grade,
        s.name AS studentName,
        c.name AS courseName,
        t.name AS instructorName,
        t.no AS instructorId,
        s.dept AS studentDept
    FROM enrollments e
    JOIN student s ON e.student_id = s.no
    JOIN course_offerings co ON e.offering_id = co.offering_id
    JOIN course c ON co.course_id = c.no
    JOIN teacher t ON co.instructor_id = t.no
""")
    @Results(id = "enrollmentResult", value = {
            @Result(column = "enrollment_id", property = "enrollmentId"),
            @Result(column = "student_id", property = "studentId"),
            @Result(column = "offering_id", property = "offeringId"),
            @Result(column = "status", property = "status"),
            @Result(column = "grade", property = "grade"),
            @Result(column = "studentName", property = "studentName"),
            @Result(column = "courseName", property = "courseName"),
            @Result(column = "instructorName", property = "instructorName"),
            @Result(column = "instructorId", property = "instructorId"),
            @Result(column = "studentDept", property = "studentDept")
    })
    List<Enrollment> getAllEnrollments();

    @Insert("""
    INSERT INTO enrollments (student_id, offering_id, status, grade)
    VALUES (#{studentId}, #{offeringId},  #{status},#{grade})
""")
    @Options(useGeneratedKeys = true, keyProperty = "enrollmentId", keyColumn = "enrollment_id") // 自动生成主键
    int insertEnrollment(Enrollment enrollment);

    @Update("""
    UPDATE enrollments
    SET student_id = #{studentId}, offering_id = #{offeringId}, status=#{status}, grade = #{grade}
    WHERE enrollment_id = #{enrollmentId}
""")
    int updateEnrollment(Enrollment enrollment);

    @ResultMap("enrollmentResult")
    @Select("""
    SELECT
        e.enrollment_id,
        e.student_id,
        e.offering_id,
        e.status,
        e.grade,
        s.name AS studentName,
        c.name AS courseName,
        t.name AS instructorName,
        t.no AS instructorId,
        s.dept AS studentDept
    FROM enrollments e
    JOIN student s ON e.student_id = s.no
    JOIN course_offerings co ON e.offering_id = co.offering_id
    JOIN course c ON co.course_id = c.no
    JOIN teacher t ON co.instructor_id = t.no
    where e.offering_id = #{offeringId}
    LIMIT #{pageSize} OFFSET #{offset}
""")
    List<Enrollment> getAllEnrollmentsByOfferingId(@Param("offeringId") int offeringId,@Param("pageSize") int pageSize, @Param("offset") int offset);


    @Select("select count(*) from enrollments where offering_id = #{offeringId}")
    int countEnrollmentByOfferingId(@Param("offeringId") int offeringId);

    @ResultMap("enrollmentResult")
    @Select("select * from enrollments where student_id = #{studentId} and offering_id = #{offeringId}")
    Enrollment getEnrollmentByStudentIdAndOfferingId(@Param("studentId") String studentId, @Param("offeringId") int offeringId);

    @ResultMap("enrollmentResult")
    @Delete("delete from enrollments where enrollment_id = #{enrollmentId}")
    int deleteEnrollmentById(@Param("enrollmentId") int enrollmentId);


    @ResultMap("enrollmentResult")
    @Select("""
    SELECT
        e.enrollment_id,
        e.student_id,
        e.offering_id,
        e.status,
        e.grade,
        s.name AS studentName,
        c.name AS courseName,
        t.name AS instructorName,
        t.no AS instructorId,
        s.dept AS studentDept
    FROM enrollments e
    JOIN student s ON e.student_id = s.no
    JOIN course_offerings co ON e.offering_id = co.offering_id
    JOIN course c ON co.course_id = c.no
    JOIN teacher t ON co.instructor_id = t.no
    where e.student_id = #{studentId}
    LIMIT #{pageSize} OFFSET #{offset}
""")
    List<Enrollment> getAllEnrollmentsByStudentId(@Param("studentId") String studentId,@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("select count(*) from enrollments where student_id = #{studentId}")
    int countEnrollmentByStudentId(@Param("studentId") String studentId);

    @ResultMap("enrollmentResult")
    @Select("""
    SELECT
        e.enrollment_id,
        e.student_id,
        e.offering_id,
        e.status,
        e.grade,
        s.name AS studentName,
        c.name AS courseName,
        t.name AS instructorName,
        t.no AS instructorId,
        s.dept AS studentDept
    FROM enrollments e
    JOIN student s ON e.student_id = s.no
    JOIN course_offerings co ON e.offering_id = co.offering_id
    JOIN course c ON co.course_id = c.no
    JOIN teacher t ON co.instructor_id = t.no
    where e.enrollment_id = #{enrollmentId}
""")
    Enrollment getTeacherCourseGrade(@Param("enrollmentId") int enrollmentId);

}
