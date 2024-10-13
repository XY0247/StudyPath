package servlet.AdminPage.Teacher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.DepartmentService;
import service.TeacherService;
import service.impl.DepartmentServiceImpl;
import service.impl.TeacherServiceImpl;
import utils.JsonUtil;
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/TeacherInfo")
public class TeacherInfoServlet extends HttpServlet {
    TeacherService teacherService;
    DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        teacherService = new TeacherServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        int itemsPerPage = 10;  // 每页显示的记录数
        int totalItems = teacherService.getTeacherCount();  // 获取总记录数

        // 分页数据
        List<Teacher> teacherList = teacherService.getTeacherByPage((currentPage - 1) * itemsPerPage, itemsPerPage);

        // 使用 PaginationHelper
        PageResult<Teacher> pagination = new PageResult<>(teacherList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("pagination", pagination);

        ThymeleafUtil.process("TeacherInfo.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);

        String teacherNo = String.valueOf(data.get("teacherNo")).replace("\"", "");

        ObjectNode jsonResponse =  JsonUtil.getObjectNode();

        if (teacherService.getTeacherById(teacherNo)== null) {
            jsonResponse.put("message","删除失败,没有该数据" );

        }
        else{
            jsonResponse.put("message","删除成功" );
            teacherService.deleteTeacher(teacherNo);
        }

        JsonUtil.sendJsonResponse(resp, jsonResponse);
    }
}
