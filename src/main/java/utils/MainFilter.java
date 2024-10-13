package utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class MainFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        //判断是否为静态资源
        if(!url.endsWith(".js") && !url.endsWith(".css") && !url.endsWith(".png")){
            HttpSession session = req.getSession();
            if (url.contains("login")) {
                chain.doFilter(req, res);
                return;
            }
            if(session.getAttribute("userAdmin")==null&&session.getAttribute("userStudent")==null&&session.getAttribute("userTeacher")==null){
                res.sendRedirect("login");
                return;
            }
            if (session.getAttribute("userTeacher")!=null) {
                if(url.contains("TeacherCourseSetting")||url.contains("TeacherCourse")||url.contains("TeacherIndex")||url.contains("EditProfile")||url.contains("logout")){
                    chain.doFilter(req, res);
                }
                else
                    res.sendRedirect("TeacherIndex");
                return;
            }
            if (session.getAttribute("userStudent")!=null) {
                if(url.contains("StudentIndex")||url.contains("StudentCourseSelection")||url.contains("StudentCourseDrop")||url.contains("EditProfile")||url.contains("logout")){
                    chain.doFilter(req, res);
                }
                else
                    res.sendRedirect("StudentIndex");
                return;
            }
        }
        //交给过滤链处理
        chain.doFilter(req, res);
    }
}
