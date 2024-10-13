package servlet.AdminPage.Teacher;

import entity.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.DepartmentService;
import service.TeacherService;
import service.impl.DepartmentServiceImpl;
import service.impl.TeacherServiceImpl;
import utils.JsonUtil;
import utils.ThymeleafUtil;

import java.io.IOException;

@WebServlet("/AddTeacher")
public class AddTeacherServlet extends HttpServlet {
    DepartmentService departmentService;
    TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        teacherService = new TeacherServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        ThymeleafUtil.process("AddTeacher.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 读取请求体中的 JSON 数据
        Teacher teacher = JsonUtil.parseJsonRequest(req, Teacher.class);
        System.out.println("收到的教师数据: " + teacher);

        // 处理逻辑
        boolean addSuccess = teacherService.addTeacherWithObject(teacher);
        String message = addSuccess ? "添加成功" : "添加失败，已存在教师ID";
        String url = "/StudyPath/TeacherInfo";

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}
