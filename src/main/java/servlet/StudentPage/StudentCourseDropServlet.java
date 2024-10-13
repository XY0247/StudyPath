package servlet.StudentPage;

import com.fasterxml.jackson.databind.JsonNode;
import entity.CourseOffering;
import entity.Enrollment;
import entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.CourseOfferingService;
import service.DepartmentService;
import service.EnrollmentService;
import service.impl.CourseOfferingServiceImpl;
import service.impl.DepartmentServiceImpl;
import service.impl.EnrollmentServiceImpl;
import utils.JsonUtil;
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/StudentCourseDrop")
public class StudentCourseDropServlet extends HttpServlet {
    DepartmentService departmentService;
    CourseOfferingService courseOfferingService;
    EnrollmentService enrollmentService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        courseOfferingService = new CourseOfferingServiceImpl();
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
        int totalItems = courseOfferingService.getSelectedCoursesCount(student.getNo());  // 获取总记录数

        // 分页数据
        List<CourseOffering> courseOfferingList = courseOfferingService.getSelectedCoursesDetails((currentPage - 1) * itemsPerPage, itemsPerPage,student.getNo());

        // 使用 PaginationHelper
        PageResult<CourseOffering> pagination = new PageResult<>(courseOfferingList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();

        context.setVariable("pagination", pagination);
        context.setVariable("user", req.getSession().getAttribute("userStudent"));

        ThymeleafUtil.process("StudentCourseDrop.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);
        int offeringId = data.get("OfferingId").asInt();
        CourseOffering courseOffering = courseOfferingService.getCourseOffering(offeringId);
        Enrollment enrollment =  enrollmentService.getEnrollmentByStudentIdAndOfferingId((Student) req.getSession().getAttribute("userStudent"),courseOffering);
        if(enrollment!=null){
            courseOffering.setCurrentEnrollment(courseOffering.getCurrentEnrollment()-1);
            courseOfferingService.updateCourseOffering(courseOffering);
            enrollmentService.deleteEnrollment(enrollment);
            JsonUtil.sendSuccessResponse(resp,"退课成功","/StudyPath/StudentCourseDrop");
        }
        else
            JsonUtil.sendSuccessResponse(resp,"已退课成功，不要重复提交","/StudyPath/StudentCourseDrop");
    }
}
