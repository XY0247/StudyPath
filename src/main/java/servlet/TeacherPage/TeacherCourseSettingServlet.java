package servlet.TeacherPage;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Enrollment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.EnrollmentService;
import service.impl.EnrollmentServiceImpl;
import utils.JsonUtil;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/TeacherCourseSetting")
public class TeacherCourseSettingServlet extends HttpServlet {

    EnrollmentService enrollmentService;

    @Override
    public void init() throws ServletException {
        enrollmentService = new EnrollmentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Context context = new Context();
        context.setVariable("enrollment", enrollmentService.getEnrollmentById(Integer.parseInt(req.getParameter("teacherCourseID"))));
        context.setVariable("user", req.getSession().getAttribute("userTeacher"));
        ThymeleafUtil.process("TeacherCourseSetting.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);
        int enrollmentId = data.get("enrollmentId").asInt();
        System.out.println(data);
        Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
        if (enrollment == null) {
            JsonUtil.sendSuccessResponse(resp,"没有该数据","/StudyPath/TeacherCourse?viewCourseOffering="+data.get("viewCourseOffering").asInt());
            return;
        }
        enrollment.setGrade(data.get("CourseGrade").asInt());
        enrollmentService.updateEnrollment(enrollment);
        JsonUtil.sendSuccessResponse(resp,"成绩提交成功","/StudyPath/TeacherCourse?viewCourseOffering="+data.get("viewCourseOffering").asInt());
    }
}
