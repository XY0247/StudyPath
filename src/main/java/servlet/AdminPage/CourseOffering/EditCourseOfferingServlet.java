package servlet.AdminPage.CourseOffering;

import entity.CourseOffering;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/EditCourseOffering")
public class EditCourseOfferingServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Slf4j.class);

    DepartmentService departmentService;
    CourseService courseService;
    TeacherService teacherService;
    CourseOfferingService courseOfferingService;
    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        courseService = new CourseServiceImpl();
        teacherService = new TeacherServiceImpl();
        courseOfferingService = new CourseOfferingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Context context = new Context();
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("courseOffering", (CourseOffering)courseOfferingService.getCourseOffering(Integer.parseInt(req.getParameter("editCourseOffering"))));
        context.setVariable("courseList", courseService.getAllCourses());
        context.setVariable("teacherList", teacherService.getAllTeachers());
        ThymeleafUtil.process("EditCourseOffering.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

// 使用 JsonUtil 工具类来解析请求体并转换为 Course 对象
        CourseOffering courseOffering = JsonUtil.parseJsonRequest(req, CourseOffering.class);
        System.out.println("收到的课程开设数据: " + courseOffering);

        // 处理逻辑
        boolean courseExists = courseOfferingService.updateCourseOffering(courseOffering);
        String message = courseExists ? "修改成功" : "修改失败";
        String url = "CourseOfferingInfo";

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}
