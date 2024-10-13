package servlet.AdminPage.Student;

import entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.DepartmentService;
import service.StudentService;
import service.impl.DepartmentServiceImpl;
import service.impl.StudentServiceImpl;
import utils.JsonUtil;
import utils.ThymeleafUtil;

import java.io.IOException;

@WebServlet("/AddStudent")
public class AddStudentServlet extends HttpServlet {
    DepartmentService departmentService;
    StudentService studentService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        studentService = new StudentServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        ThymeleafUtil.process("AddStudent.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 读取请求体中的 JSON 数据
        Student student = JsonUtil.parseJsonRequest(req, Student.class);
        System.out.println("收到的学生数据: " + student);

        String message;
        String url = "/StudyPath/StudentInfo?deptStudent="+student.getDept();
        // 处理逻辑
        if(!studentService.addStudentWithObject(student)){
            message = "已有该学生号";
        }
        else {
            message = "添加成功";
        }

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}
