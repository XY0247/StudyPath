package servlet.AdminPage.CourseOffering;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.CourseOffering;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import service.CourseOfferingService;
import service.DepartmentService;
import service.impl.CourseOfferingServiceImpl;
import service.impl.DepartmentServiceImpl;
import utils.JsonUtil;
import utils.PageResult;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/CourseOfferingInfo")
public class CourseOfferingInfoServlet extends HttpServlet {
    DepartmentService departmentService;
    CourseOfferingService courseOfferingService;

    @Override
    public void init() throws ServletException {
        departmentService = new DepartmentServiceImpl();
        courseOfferingService = new CourseOfferingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int currentPage = 1;  // 默认第一页
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        int itemsPerPage = 10;  // 每页显示的记录数
        int totalItems = courseOfferingService.getCourseOfferingsCount();  // 获取总记录数

        // 分页数据
        List<CourseOffering> courseOfferingList = courseOfferingService.getCourseOfferingsByPage((currentPage - 1) * itemsPerPage, itemsPerPage);

        // 使用 PaginationHelper
        PageResult<CourseOffering> pagination = new PageResult<>(courseOfferingList, currentPage, totalItems, itemsPerPage);

        Context context = new Context();
        context.setVariable("user", req.getSession().getAttribute("userAdmin"));
        context.setVariable("departments", departmentService.getAllDepartments());
        context.setVariable("pagination", pagination);

        ThymeleafUtil.process("CourseOfferingInfo.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode data = JsonUtil.parseJsonRequest(req);
        int courseOfferingNo = data.get("courseOfferingNo").asInt();

        ObjectNode jsonResponse =  JsonUtil.getObjectNode();
        System.out.println(courseOfferingNo);
        if (courseOfferingService.getCourseOffering(courseOfferingNo)== null) {
            jsonResponse.put("message","删除失败,没有该数据" );

        }
        else{
            jsonResponse.put("message","删除成功" );
            courseOfferingService.deleteCourseOffering(courseOfferingNo);
        }

        JsonUtil.sendJsonResponse(resp, jsonResponse);
    }
}
