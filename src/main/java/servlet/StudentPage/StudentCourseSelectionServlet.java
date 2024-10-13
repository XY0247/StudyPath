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
import java.util.Objects;

@WebServlet("/StudentCourseSelection")
public class StudentCourseSelectionServlet extends HttpServlet {
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
        int totalItems = courseOfferingService.getUnselectedCoursesCount(student.getNo());  // 获取总记录数

        // 分页数据
        List<CourseOffering> courseOfferingList = courseOfferingService.getUnselectedCoursesDetails((currentPage - 1) * itemsPerPage, itemsPerPage,student.getNo());

        // 使用 PaginationHelper
        PageResult<CourseOffering> pagination = new PageResult<>(courseOfferingList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();

        context.setVariable("pagination", pagination);
        context.setVariable("user", req.getSession().getAttribute("userStudent"));

        ThymeleafUtil.process("StudentCourseSelection.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);
        int offeringId = data.get("OfferingId").asInt();
        System.out.println(offeringId);
        Student student = (Student) req.getSession().getAttribute("userStudent");
        CourseOffering courseOffering = courseOfferingService.getCourseOffering(offeringId);
        if(courseOffering.getCurrentEnrollment()==courseOffering.getMaxEnrollment()){
            JsonUtil.sendSuccessResponse(resp,"该课程已经选满!","/StudentPage/StudentCourseSelection");
            return;
        }
        Enrollment enrollment =  enrollmentService.getEnrollmentByStudentIdAndOfferingId(student,courseOffering);
        if(enrollment!=null&& Objects.equals(enrollment.getStatus(), "已选")){
            JsonUtil.sendSuccessResponse(resp,"已选课成功，不要重复提交","/StudyPath/StudentCourseSelection");
            enrollmentService.updateEnrollment(enrollment);
        }
        else{
            courseOffering.setCurrentEnrollment(courseOffering.getCurrentEnrollment()+1);
            courseOfferingService.updateCourseOffering(courseOffering);
            enrollment = new Enrollment();
            enrollment.setStudentId(student.getNo());
            enrollment.setOfferingId(courseOffering.getOfferingId());
            enrollment.setStatus("已选");
            System.out.println(enrollment);
            enrollmentService.addEnrollment(enrollment);
            JsonUtil.sendSuccessResponse(resp,"选课成功","/StudyPath/StudentCourseSelection");
        }
    }
}
