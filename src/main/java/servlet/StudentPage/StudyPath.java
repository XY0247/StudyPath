package servlet.StudentPage;

import entity.CourseOffering;
import entity.Enrollment;
import entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.EnrollmentService;
import service.impl.EnrollmentServiceImpl;
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/StudentIndex")
public class StudyPath extends HttpServlet {
    EnrollmentService enrollmentService;

    @Override
    public void init() throws ServletException {
        enrollmentService = new EnrollmentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        Student student = (Student) req.getSession().getAttribute("userStudent");

        int itemsPerPage = 10;  // 每页显示的记录数
        int totalItems = enrollmentService.getEnrollmentCountByStudentId(student.getNo());  // 获取总记录数

        // 分页数据
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentsByStudentId(itemsPerPage, (currentPage - 1) * itemsPerPage,student.getNo());

        // 使用 PaginationHelper
        PageResult<Enrollment> pagination = new PageResult<>(enrollmentList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();
        context.setVariable("pagination", pagination);
        context.setVariable("user", req.getSession().getAttribute("userStudent"));

        ThymeleafUtil.process("StudentIndex.html",context,resp.getWriter());
    }
}
