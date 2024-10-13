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

@WebServlet("/AddCourse")
public class AddCourseServlet extends HttpServlet {
    DepartmentService departmentService;
    CourseService courseService;
    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        courseService = new CourseServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("courseList", courseService.getAllCourses());
        ThymeleafUtil.process("AddCourse.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 使用 JsonUtil 工具类来解析请求体并转换为 Course 对象
        Course course = JsonUtil.parseJsonRequest(req, Course.class);
        System.out.println("收到的课程数据: " + course);

        boolean courseExists = courseService.updateCourseWithObject(course);
        String message;
        String url = "CourseInfo";
        // 处理逻辑
        if(!courseService.isCourseExist(course.getNo())){
            message = "已有该课程号";
        }
        else{
            courseService.addCourseWithObject(course);
            message = "添加成功";
        }

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}
