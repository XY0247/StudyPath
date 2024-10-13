package servlet.AdminPage.Course;

import entity.Course;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.CourseService;
import service.DepartmentService;
import service.impl.CourseServiceImpl;
import service.impl.DepartmentServiceImpl;
import utils.JsonUtil;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/EditCourse")
public class EditCourseServlet extends HttpServlet {
    DepartmentService departmentService;
    CourseService courseService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        courseService = new CourseServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Context context = new Context();
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("editCourse", courseService.getCourseByNo(req.getParameter("editCourse")));


        List<Course> courseList =courseService.getAllCourses();
        courseList.removeIf(course -> Objects.equals(course.getNo(), req.getParameter("editCourse")));
        context.setVariable("courseList",courseList);
        ThymeleafUtil.process("EditCourse.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

// 使用 JsonUtil 工具类来解析请求体并转换为 Course 对象
        Course course = JsonUtil.parseJsonRequest(req, Course.class);
        System.out.println("收到的课程数据: " + course);

        // 处理逻辑
        boolean courseExists = courseService.updateCourseWithObject(course);
        String message = courseExists ? "修改成功" : "修改失败";
        String url = "CourseInfo";

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}
