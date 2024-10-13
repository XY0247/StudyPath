package servlet.AdminPage;
import entity.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.*;
import service.impl.*;
import utils.ThymeleafUtil;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/index")
public class StudyPath extends HttpServlet {

    DepartmentService departmentService;
    StudentService studentService;
    EnrollmentService enrollmentService;
    TeacherService teacherService;
    CourseService courseService;
    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        studentService = new StudentServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        teacherService = new TeacherServiceImpl();
        courseService = new CourseServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Context context = new Context();
        List<Department> departments = departmentService.getAllDepartments();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departments);


        List<String> categories = Arrays.asList("体育学院", "外国语学院", "政法学院", "教育学院", "数学学院", "文学院", "旅游学院", "机械学院", "环化学院", "生科学院", "电子工程学院", "经管学院", "美术学院", "计算机学院", "音乐学院", "制药学院");
        List<Double> series = new ArrayList<>();

        DecimalFormat decimalFormat = new DecimalFormat("#.#"); // 创建格式化器，限制为一位小数
        int totalStudentsCount = studentService.getStudentsCount();
        for (Department department : departments) {
            int count = studentService.getStudentsCountByDept(department.getName());
            double percentage = totalStudentsCount > 0 ? (count / (double) totalStudentsCount) * 100 : 0.0;

            // 格式化为一位小数并转换为 Double
            double formattedPercentage = Double.parseDouble(decimalFormat.format(percentage));

            series.add(formattedPercentage);
        }
        context.setVariable("departmentCount", departmentService.getDepartmentCount());
        context.setVariable("teacherCount", teacherService.getTeacherCount());
        context.setVariable("studentCount", studentService.getStudentsCount());
        context.setVariable("courseCount", courseService.getCoursesCount());
        context.setVariable("categories", categories);
        context.setVariable("series", series);

        ThymeleafUtil.process("index.html", context, resp.getWriter());
    }
}
