package service.impl;

import dao.DepartmentMapper;
import entity.Department;
import service.DepartmentService;
import utils.MybatisUtil;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public List<Department> getAllDepartments() {
        return MybatisUtil.execute(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.selectAll();
        });
    }

    @Override
    public Department getDepartmentsByName(String departmentName) {
        return MybatisUtil.execute(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.selectDepartmentByName(departmentName);
        });
    }

    @Override
    public boolean addDepartment(Department department) {
        return MybatisUtil.execute(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.insertDepartmentWithObject(department)>0;
        });
    }

    @Override
    public boolean updateDepartment(Department department) {
        return MybatisUtil.execute(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.updateDepartmentWithObject(department)>0;
        });
    }

    @Override
    public int getDepartmentCount() {
        return MybatisUtil.execute(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.countDepartment();
        });
    }

    @Override
    public List<Department> getDepartByPage(int currentPage, int pageSize) {
        return MybatisUtil.execute(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.selectDepartmentsByPage(pageSize, currentPage);
        });
    }

    @Override
    public boolean deleteDepartment(String department) {
        return MybatisUtil.execute(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.deleteDepartmentByName(department)>0;
        });
    }
}
