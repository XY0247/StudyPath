package service.impl;

import dao.AdminMapper;
import entity.Admin;
import service.AdminService;
import utils.MybatisUtil;

import java.util.List;

public class AdminServiceImp implements AdminService {
    @Override
    public List<Admin> getAdmins() {
        return MybatisUtil.execute(sqlSession -> {
            AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
            return mapper.selectAll();
        });
    }

    @Override
    public Admin getAdmin(String id) {
        return MybatisUtil.execute(sqlSession -> {
            AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
            return mapper.selectByNo(id);
        });
    }

    @Override
    public Admin AdminLogin(String id, String password) {
        return MybatisUtil.execute(sqlSession -> {
            AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
            return mapper.selectByNoAndPassword(id,password);
        });
    }

    @Override
    public boolean addAdmin(Admin admin) {
        return MybatisUtil.execute(sqlSession -> {
            AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
            return mapper.insert(admin)>0;
        });
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        return MybatisUtil.execute(sqlSession -> {
            AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
            return mapper.updateAdmin(admin)>0;
        });
    }

    @Override
    public boolean deleteAdmin(String id) {
        return MybatisUtil.execute(sqlSession -> {
            AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
            return mapper.deleteByNo(id)>0;
        });
    }
}
