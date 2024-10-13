package dao;

import entity.Course;
import entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CourseMapper {

    @Results(id = "CourseMapper",value = {
            @Result(id = true, column = "no", property = "no"),
            @Result(column = "name", property = "name"),
            @Result(column = "pno", property = "pno"),
            @Result(column = "credit", property = "credit"),
            @Result(column = "type", property = "type")
    })

    @ConstructorArgs({
            @Arg(id = true, column = "no", javaType = String.class),
            @Arg(column = "name", javaType = String.class),
            @Arg(column = "pno", javaType = String.class),
            @Arg(column = "credit", javaType = int.class),
            @Arg(column = "type", javaType = String.class),
    })

    @Select("select * from course")
    List<Course> selectAll();

    @ResultMap("courseResult")
    @Select("""
        SELECT
            c.no,
            c.name,
            c.pno,
            c.credit,
            c.type,
            IFNULL(p.name, '') AS pnoName
        FROM Course c
        LEFT JOIN Course p ON c.pno = p.no
        WHERE c.no = #{no}
    """)
    Course selectByNo(@Param("no") String no);

    @ResultMap("CourseMapper")
    @Insert("insert into course (no, name, pno, credit,type) values (#{no},#{name},#{pno},#{credit},#{type})")
    int insertCourseWithObject(Course course);

    @ResultMap("CourseMapper")
    @Update("update course set name = #{name}, pno = #{pno}, credit = #{credit} ,type = #{type} where no = #{no}")
    int updateCourseWithObject(Course course);

    @ResultMap("CourseMapper")
    @Select("select * from course")
    List<Course> selectCourseAllWithRowBounds(RowBounds rowBounds);

    @Select("""
        SELECT
            c.no,
            c.name,
            c.pno,
            c.credit,
            c.type,
            IFNULL(p.name, '') AS pnoName
        FROM Course c
        LEFT JOIN Course p ON c.pno = p.no
        LIMIT #{pageSize} OFFSET #{offset}
    """)
    @Results(id = "courseResult", value = {
            @Result(column = "no", property = "no"),
            @Result(column = "name", property = "name"),
            @Result(column = "pno", property = "pno"),
            @Result(column = "credit", property = "credit"),
            @Result(column = "type", property = "type"),
            @Result(column = "pnoName", property = "pnoName") // 确保这行正确
    })
    List<Course> getAllCoursesWithPnoName(@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("select count(*) from course")
    int countCourse();

    @Select("select count(*) from course where type=#{type}")
    int countCourseByType(@Param("type") String type);

    @Select("""
        SELECT
            c.no,
            c.name,
            c.pno,
            c.credit,
            c.type,
            IFNULL(p.name, '') AS pnoName
        FROM Course c
        LEFT JOIN Course p ON c.pno = p.no
        WHERE c.type = #{courseType}
        LIMIT #{pageSize} OFFSET #{offset}
    """)
    @ResultMap("courseResult")
    List<Course> getCoursesByType(@Param("courseType") String courseType,@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Delete("delete from course where no = #{no}")
    int deleteCourseByNo(@Param("no") String no);
}
