package servlet.AdminPage.CourseOffering;

import entity.Enrollment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.*;
import service.impl.*;
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/ViewCourseOffering")
public class ViewCourseOfferingServlet extends HttpServlet {

    DepartmentService departmentService;
    EnrollmentService enrollmentService;
    CourseOfferingService courseOfferingService;
    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        enrollmentService =  new EnrollmentServiceImpl();
        courseOfferingService = new CourseOfferingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int OfferingID = Integer.parseInt(req.getParameter("viewCourseOffering"));
        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        int itemsPerPage = 10;  // 每页显示的记录数
        int totalItems = enrollmentService.getEnrollmentCountByOfferingId(OfferingID);  // 获取总记录数

        // 分页数据
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentsByOfferingId(OfferingID,(currentPage - 1) * itemsPerPage, itemsPerPage);

        // 使用 PaginationHelper
        PageResult<Enrollment> pagination = new PageResult<>(enrollmentList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();
        context.setVariable("OfferingID", OfferingID);
        context.setVariable("OfferingNAME", courseOfferingService.getCourseOffering(OfferingID).getCourseName());
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("pagination", pagination);

        ThymeleafUtil.process("ViewCourseOffering.html",context,resp.getWriter());

    }
}
