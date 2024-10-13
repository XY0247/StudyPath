package servlet.AdminPage.Student;


import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.DepartmentService;
import service.StudentService;
import service.impl.DepartmentServiceImpl;
import service.impl.StudentServiceImpl;
import utils.JsonUtil;
import utils.PageResult;
import utils.ThymeleafUtil;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@WebServlet("/StudentInfo")
public class StudentInfoServlet extends HttpServlet {
    DepartmentService departmentService;
    StudentService studentService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String dept = (String) req.getParameter("deptStudent");

        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        int itemsPerPage = 10;  // 每页显示的记录数
        int totalItems = studentService.getStudentsCountByDept(dept);  // 获取总记录数

        // 分页数据
        List<Student> studentList = studentService.getStudentsByDeptAndPage(dept,(currentPage - 1) * itemsPerPage, itemsPerPage);

        // 使用 Pagination
        PageResult<Student> pagination = new PageResult<>(studentList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("pagination", pagination);

        ThymeleafUtil.process("StudentInfo.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);

        String studentNo = String.valueOf(data.get("studentNo")).replace("\"", "");

        ObjectNode jsonResponse =  JsonUtil.getObjectNode();

        if (studentService.getStudentByNo(studentNo) == null) {
            jsonResponse.put("message","删除失败,没有该数据" );

        }
        else{
            jsonResponse.put("message","删除成功" );
            studentService.deleteStudentByNo(studentService.getStudentByNo(studentNo));
        }

        JsonUtil.sendJsonResponse(resp, jsonResponse);
    }
}
