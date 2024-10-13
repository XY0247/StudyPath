package service;

import entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentsByName(String departmentName);
    boolean addDepartment(Department department);
    boolean updateDepartment(Department department);
    int getDepartmentCount();
    List<Department> getDepartByPage (int currentPage, int pageSize);
    boolean deleteDepartment(String department);
}
