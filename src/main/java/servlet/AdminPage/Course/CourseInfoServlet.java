package servlet.AdminPage.Course;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;

@WebServlet("/CourseInfo")
public class CourseInfoServlet extends HttpServlet {
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
        context.setVariable("pagination",getCheckCoursesType(req));
        ThymeleafUtil.process("CourseInfo.html",context,resp.getWriter());
    }

    private PageResult<Course> getCheckCoursesType(HttpServletRequest req) {

        String param = req.getParameter("CourseType");
        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        int itemsPerPage = 10;  // 每页显示的记录数


        if (param == null || param.equals("全部")) {
            int totalItems = courseService.getCoursesCount();  // 获取总记录数
            return new PageResult<>(courseService.getAllCoursesWithPnoName((currentPage - 1) * itemsPerPage, itemsPerPage), currentPage, totalItems, itemsPerPage);
        }
        int totalItems = courseService.getCoursesCountByType(param);  // 获取总记录数
        return new PageResult<>(courseService.getCoursesByType(param,(currentPage - 1) * itemsPerPage, itemsPerPage), currentPage, totalItems, itemsPerPage);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);

        String courseNo = String.valueOf(data.get("courseNo")).replace("\"", "");

        ObjectNode jsonResponse =  JsonUtil.getObjectNode();

        if (courseService.getCourseByNo(courseNo)== null) {
            jsonResponse.put("message","删除失败,没有该数据" );

        }
        else{
            jsonResponse.put("message","删除成功" );
            courseService.deleteCourse(courseNo);
        }

        JsonUtil.sendJsonResponse(resp, jsonResponse);
    }
}
