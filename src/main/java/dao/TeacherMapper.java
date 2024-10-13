package dao;
import entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TeacherMapper {

    @Results(id = "TeachMapper",value = {
            @Result(id = true, column = "no", property = "no"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "teb", property = "teb"),
            @Result(column = "tpt", property = "tpt"),
            @Result(column = "level", property = "level"),
            @Result(column = "password", property = "password")
    })
    @ConstructorArgs({
            @Arg(id = true, column = "no", javaType = String.class),
            @Arg(column = "name", javaType = String.class),
            @Arg(column = "sex", javaType = String.class),
            @Arg(column = "age", javaType = int.class),
            @Arg(column = "teb", javaType = String.class),
            @Arg(column = "tpt", javaType = String.class),
            @Arg(column = "level", javaType = int.class),
            @Arg(column = "password", javaType = String.class)
    })
    @Select("select * from teacher")
    List<Teacher> selectAll();

    @ResultMap("TeachMapper")
    @Insert("insert into teacher(no, name, sex, age, teb, tpt,password,level) " +
            "VALUES (#{no},#{name},#{sex},#{age},#{teb},#{tpt},#{password},#{level})")
    int insertTeacherWithObject(Teacher teacher);


    @ResultMap("TeachMapper")
    @Update("update teacher set name = #{name}, sex = #{sex}, age = #{age}, teb = #{teb}," +
            "tpt = #{tpt},password=#{password},level=#{level} where no = #{no}")
    int updateTeacherWithObject(Teacher teacher);

    @ResultMap("TeachMapper")
    @Select("select * from teacher where no = #{no} and password=#{password}")
    Teacher selectTeacherByNoAndPassword(@Param("no")String no, @Param("password")String password);

    @ResultMap("TeachMapper")
    @Select("select * from teacher where no = #{no}")
    Teacher selectTeacherByNo(@Param("no") String no);

    @ResultMap("TeachMapper")
    @Select("SELECT * FROM teacher LIMIT #{pageSize} OFFSET #{offset}")
    List<Teacher> selectTeachersByPage(@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("select count(*) from teacher")
    int countTeacher();

    @Delete("delete from teacher where no = #{no}")
    int deleteTeacherByNo(@Param("no") String no);
}
