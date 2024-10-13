package servlet.TeacherPage;

import entity.CourseOffering;
import entity.Enrollment;
import entity.Student;
import entity.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.CourseOfferingService;
import service.TeacherService;
import service.impl.CourseOfferingServiceImpl;
import service.impl.TeacherServiceImpl;
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/TeacherIndex")
public class StudyPathServlet extends HttpServlet {

    CourseOfferingService courseOfferingService;
    TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        courseOfferingService = new CourseOfferingServiceImpl();
        teacherService = new TeacherServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        Teacher teacher = (Teacher) req.getSession().getAttribute("userTeacher");

        int itemsPerPage = 10;  // 每页显示的记录数
        int totalItems = courseOfferingService.getTeacherCoursesCount(teacher.getNo());  // 获取总记录数

        // 分页数据
        List<CourseOffering> courseOfferingList = courseOfferingService.getTeacherCourses((currentPage - 1) * itemsPerPage,itemsPerPage, teacher.getNo());
        // 使用 PaginationHelper
        PageResult<CourseOffering> pagination = new PageResult<>(courseOfferingList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();
        context.setVariable("pagination", pagination);
        context.setVariable("user", req.getSession().getAttribute("userTeacher"));
        ThymeleafUtil.process("TeacherIndex.html",context,resp.getWriter());
    }
}
