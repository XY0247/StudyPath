package servlet.AdminPage.Department;

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
import utils.ThymeleafUtil;

import java.io.IOException;

@WebServlet("/EditDepartment")
public class EditDepartmentServlet extends HttpServlet {
    DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("department", departmentService.getDepartmentsByName((String) req.getParameter("editDepartment")));

        ThymeleafUtil.process("EditDepartment", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

// 使用 JsonUtil 工具类来解析请求体并转换为 Course 对象
        Department department = JsonUtil.parseJsonRequest(req, Department.class);
        System.out.println("收到的学院数据: " + department);

        // 处理逻辑
        boolean departmentExists = departmentService.updateDepartment(department);
        String message = departmentExists ? "修改成功" : "修改失败";
        String url = "DepartmentInfo";

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}

