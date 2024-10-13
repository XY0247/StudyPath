package servlet;

import entity.Admin;
import entity.Student;
import entity.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.AdminService;
import service.DepartmentService;
import service.StudentService;
import service.TeacherService;
import service.impl.AdminServiceImp;
import service.impl.DepartmentServiceImpl;
import service.impl.StudentServiceImpl;
import service.impl.TeacherServiceImpl;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/EditProfile")
public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        String type = (String) req.getSession().getAttribute("userType");
        context.setVariable("userType",type);
        if(type.equals("admin")){
            System.out.println("admin");
            DepartmentService departmentService = new DepartmentServiceImpl();
            context.setVariable("user", req.getSession().getAttribute("userAdmin"));
            context.setVariable("departments", departmentService.getAllDepartments());
        }
        else if(type.equals("student")){
            context.setVariable("user", req.getSession().getAttribute("userStudent"));
        }
        else {
            context.setVariable("user", req.getSession().getAttribute("userTeacher"));
        }
        ThymeleafUtil.process("EditProfile.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String phone = req.getParameter("phone");
        String type = (String) req.getSession().getAttribute("userType");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        boolean result = false;

        if(type.equals("admin")){
            AdminService adminService = new AdminServiceImp();
            Admin admin =adminService.getAdmin(user);
            if(admin.getPassword().equals(oldPassword)){
                admin.setPassword(newPassword);
                adminService.updateAdmin(admin);
                result = true;
            }
        }
        else if(type.equals("student")){
            StudentService studentService = new StudentServiceImpl();
            Student student = studentService.getStudentByNo(user);
            if(student.getPassword().equals(oldPassword)){
                student.setPassword(newPassword);
                student.setPhone(phone);
                studentService.upStudentByNo(student);
                result = true;
            }
        }
        else {
            TeacherService teacherService = new TeacherServiceImpl();
            Teacher teacher = teacherService.getTeacherById(user);
            if(teacher.getPassword().equals(oldPassword)){
                teacher.setPassword(newPassword);
                teacherService.updateTeacherWithObject(teacher);
                result = true;
            }
        }

        if(result){
            out.println("<script>alert('修改成功,请重新登录');  window.location.href = `/StudyPath/login`</script>");
        }
        else
            out.println("<script>alert('修改失败,旧的密码错误');  window.location.href = `/StudyPath/EditProfile`;</script>");
    }
}
