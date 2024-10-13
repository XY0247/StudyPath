package servlet;

import entity.Admin;
import entity.Student;
import entity.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.AdminService;
import service.StudentService;
import service.TeacherService;
import service.impl.AdminServiceImp;
import service.impl.StudentServiceImpl;
import service.impl.TeacherServiceImpl;
import utils.ThymeleafUtil;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    TeacherService teacherService;
    StudentService studentService;
    AdminService adminService;

    @Override
    public void init() throws ServletException {
        teacherService = new TeacherServiceImpl();
        studentService = new StudentServiceImpl();
        adminService = new AdminServiceImp();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setMaxInactiveInterval(60*10);
        Context context = new Context();
        context.setVariable("loginMessage",req.getSession().getAttribute("loginMessage"));
        Cookie[] cookies = req.getCookies();
        String username = null;
        String password = null;
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("username")) username = cookie.getValue();
                if(cookie.getName().equals("password")) password = cookie.getValue();
            }
        }
        context.setVariable("username", username);
        context.setVariable("password", password);
        ThymeleafUtil.process("login.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");//on || null
        if(remember!=null && remember.equals("on")){
            UserCookie(resp,user,password);
        }
        if(studentService.login(user,password)!=null){
            //user:2000083149425
            //password:123456
            Student student = studentService.login(user,password);
            req.getSession().setAttribute("userStudent",student);
            req.getSession().setAttribute("userType","student");
            resp.sendRedirect("StudentIndex");
            if(req.getSession().getAttribute("loginMessage")!=null){
                req.getSession().removeAttribute("loginMessage");
            }
            return;
        }
        else if ( teacherService.loginTeacher(user,password)!=null){
            Teacher teacher = teacherService.loginTeacher(user,password);
            req.getSession().setAttribute("userTeacher",teacher);
            resp.sendRedirect("TeacherIndex");
            req.getSession().setAttribute("userType","teacher");
            if(req.getSession().getAttribute("loginMessage")!=null){
                req.getSession().removeAttribute("loginMessage");
            }
            return;
        }
        else if (adminService.AdminLogin(user,password)!=null){
            Admin admin = adminService.AdminLogin(user,password);
            req.getSession().setAttribute("userAdmin",admin);
            resp.sendRedirect("index");
            req.getSession().setAttribute("userType","admin");
            if(req.getSession().getAttribute("loginMessage")!=null){
                req.getSession().removeAttribute("loginMessage");
            }
            return;
        }
        req.getSession().setAttribute("loginMessage",true);
        resp.sendRedirect("login");
    }

    void UserCookie(HttpServletResponse resp, String user, String password) {
        Cookie cookie = new Cookie("username",user);
        cookie.setMaxAge(60*60*24*7);
        Cookie cookie2 = new Cookie("password",password);
        cookie2.setMaxAge(60*60*24*7);
        resp.addCookie(cookie);
        resp.addCookie(cookie2);
    }
}
