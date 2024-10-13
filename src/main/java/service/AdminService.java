package service;

import entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAdmins();
    Admin getAdmin(String id);
    Admin AdminLogin(String id, String password);
    boolean addAdmin(Admin admin);
    boolean updateAdmin(Admin admin);
    boolean deleteAdmin(String id);
}
