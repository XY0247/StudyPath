package dao;

import entity.Admin;
import entity.CourseOffering;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminMapper {
    @Results(id = "AdminMapper",value = {
            @Result(id = true, column = "no", property = "no"),
            @Result(column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "level", property = "level")
    })
    @Select("select * from admin")
    List<Admin> selectAll();

    @ResultMap("AdminMapper")
    @Select("select * from admin where no = #{no} and password = #{password}")
    Admin selectByNoAndPassword(@Param("no")String no, @Param("password")String password);

    @ResultMap("AdminMapper")
    @Select("select * from admin where no = #{no}")
    Admin selectByNo(@Param("no")String no);

    @ResultMap("AdminMapper")
    @Insert("""
        INSERT INTO admin (
            no,
            name,
            password,
            level
        ) VALUES (
            #{no},
            #{name},
            #{password},
            #{level}
        )
    """)
    int insert(Admin admin);

    @Update("""
    UPDATE admin
    SET
        name = #{name},
        password = #{password},
        level = #{level}
    WHERE no = #{no}
""")
    int updateAdmin(Admin admin);

    @Delete("delete from admin where no = #{no}")
    int deleteByNo(@Param("no")String no);
}
