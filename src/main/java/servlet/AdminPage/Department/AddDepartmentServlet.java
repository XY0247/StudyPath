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

@WebServlet("/AddDepartment")
public class AddDepartmentServlet extends HttpServlet {

    DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        ThymeleafUtil.process("AddDepartment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

// 使用 JsonUtil 工具类来解析请求体并转换为 Course 对象
        Department department = JsonUtil.parseJsonRequest(req, Department.class);
        System.out.println("收到的学院数据: " + department);

        // 处理逻辑
        boolean departmentExists = departmentService.addDepartment(department);
        String message = departmentExists ? "添加成功" : "添加失败，学院名字重复！";
        String url = "DepartmentInfo";

        // 使用 JsonUtil 工具类发送 JSON 响应
        JsonUtil.sendSuccessResponse(resp, message, url);
    }
}
