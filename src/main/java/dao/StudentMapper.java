package dao;

import entity.Student;
import entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface StudentMapper {

    @Results(id = "StudentMapper",value = {
            @Result(id = true, column = "no", property = "no"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "dept", property = "dept"),
            @Result(column = "password", property = "password"),
            @Result(column = "level", property = "level"),
            @Result(column = "phone", property = "phone")
    })

    @ConstructorArgs({
            @Arg(id = true, column = "no", javaType = String.class),
            @Arg(column = "name", javaType = String.class),
            @Arg(column = "sex", javaType = String.class),
            @Arg(column = "age", javaType = int.class),
            @Arg(column = "dept", javaType = String.class),
            @Arg(column = "password", javaType = String.class),
            @Arg(column = "level", javaType = int.class),
            @Arg(column = "phone", javaType = String.class)
    })

    @Select("select * from student")
    List<Student> selectAll();

    @ResultMap("StudentMapper")
    @Select("select * from student where no=#{no}")
    Student selectStudentByNo(@Param("no") String no);

    @ResultMap("StudentMapper")
    @Select("select * from student where no=#{no}")
    Student isStudentExistByNo(@Param("no") String no);

    @ResultMap("StudentMapper")
    @Insert("insert into student(no,name,sex,age,dept,password,level,phone) values(#{no},#{name},#{sex},#{age},#{dept},#{password},#{level},#{phone})")
    int insertStudentWithObject(Student student);

    @ResultMap("StudentMapper")
    @Update("update student set name=#{name},sex=#{sex},age=#{age},dept=#{dept},level=#{level},phone=#{phone},password=#{password},level=#{level} where no=#{no}")
    int updateStudentWithObject(Student student);

    @ResultMap("StudentMapper")
    @Delete("delete from student where no = #{no}")

    int deleteStudentByNo(@Param("no") String no);


    @ResultMap("StudentMapper")
    @Delete("delete from student where dept = #{dept}")
    int deleteStudentByDept(@Param("dept") String dept);


    @ResultMap("StudentMapper")
    @Select("select * from student where no = #{no} and password = #{pwd}")
    Student selectStudentByNoAndPassword(@Param("no") String no, @Param("pwd") String password);

    @ResultMap("StudentMapper")
    @Select("SELECT * FROM student where student.dept = #{dept} LIMIT #{pageSize} OFFSET #{offset} ")
    List<Student> selectStudentsByDeptAndPage(@Param("dept")String dept ,@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("select count(*) from student where dept = #{dept}")
    int countStudentsByDept(@Param("dept") String dept);

    @Select("select count(*) from student")
    int countStudents();

}
