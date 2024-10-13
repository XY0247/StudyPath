package dao;

import entity.Department;
import entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DepartmentMapper {


    @Results(id = "DepMapper",value = {
            @Result(id = true,column = "name", property = "name"),
            @Result(column = "no", property = "no"),
            @Result(column = "manager", property = "manager")
    })

    @ConstructorArgs({
            @Arg(id = true, column = "name", javaType = String.class),
            @Arg(column = "no", javaType = String.class),
            @Arg(column = "manager", javaType = String.class),
    })

    @Select("select * from department")
    List<Department> selectAll();

    @ResultMap("DepMapper")
    @Select("select * from department where name = #{name}")
    Department selectDepartmentByName(@Param("name") String name);

    @ResultMap("DepMapper")
    @Update("update department set no = #{no},manager = #{manager} where name = #{name}")
    int updateDepartmentWithObject(Department department);

    @ResultMap("DepMapper")
    @Insert("insert into department(name, no, manager) values (#{name},#{no},#{manager})")
    int insertDepartmentWithObject(Department department);

    @ResultMap("DepMapper")
    @Select("SELECT * FROM department LIMIT #{pageSize} OFFSET #{offset}")
    List<Department> selectDepartmentsByPage(@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("select count(*) from department")
    int countDepartment();

    @Delete("delete from department where name = #{name}")
    int deleteDepartmentByName(@Param("name") String name);
}
