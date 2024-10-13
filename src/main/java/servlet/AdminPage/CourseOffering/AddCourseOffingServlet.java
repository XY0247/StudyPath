package servlet.AdminPage.CourseOffering;

import entity.CourseOffering;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.CourseOfferingService;
import service.CourseService;
import service.DepartmentService;
import service.TeacherService;
import service.impl.CourseOfferingServiceImpl;
import service.impl.CourseServiceImpl;
import service.impl.DepartmentServiceImpl;
import service.impl.TeacherServiceImpl;
import utils.JsonUtil;
import utils.ThymeleafUtil;

import java.io.IOException;

@WebServlet("/AddCourseOffering")
public class AddCourseOffingServlet extends HttpServlet {
    DepartmentService departmentService;
    CourseOfferingService courseOfferingService;
    CourseService courseService;
    TeacherService teacherService;
    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        courseOfferingService = new CourseOfferingServiceImpl();
        courseService = new CourseServiceImpl();
        teacherService = new TeacherServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("courseList", courseService.getAllCourses());
        context.setVariable("teacherList", teacherService.getAllTeachers());
        ThymeleafUtil.process("AddCourseOffing.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

// 使用 JsonUtil 工具类来解析请求体并转换为 Course 对象
        CourseOffering courseOffering = JsonUtil.parseJsonRequest(req, CourseOffering.class);
        System.out.println("收到的课程开设数据: " + courseOffering);

        // 处理逻辑
        boolean courseExists = courseOfferingService.addCourseOffering(courseOffering);
        String message = courseExists ? "添加成功" : "添加失败";
        String url = "CourseOfferingInfo";

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}
