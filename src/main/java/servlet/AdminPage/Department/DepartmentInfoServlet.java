package servlet.AdminPage.Department;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.DepartmentService;
import service.impl.DepartmentServiceImpl;
import utils.JsonUtil;
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/DepartmentInfo")
public class DepartmentInfoServlet extends HttpServlet {
    DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        int itemsPerPage = 10;  // 每页显示的记录数
        int totalItems = departmentService.getDepartmentCount();  // 获取总记录数

        // 分页数据
        List<Department> departmentList = departmentService.getDepartByPage((currentPage - 1) * itemsPerPage, itemsPerPage);

        // 使用 PaginationHelper
        PageResult<Department> pagination = new PageResult<>(departmentList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("pagination", pagination);

        ThymeleafUtil.process("DepartmentInfo.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);

        String deptName = String.valueOf(data.get("departmentName")).replace("\"", "");

        ObjectNode jsonResponse =  JsonUtil.getObjectNode();

        if (departmentService.getDepartmentsByName(deptName)== null) {
            jsonResponse.put("message","删除失败,没有该数据" );

        }
        else{
            jsonResponse.put("message","删除成功" );
            departmentService.deleteDepartment(deptName);
        }

        JsonUtil.sendJsonResponse(resp, jsonResponse);
    }
}
