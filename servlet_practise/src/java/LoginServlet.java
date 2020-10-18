package java;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/18
 * @Version 1.0
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("进入方法");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //将req和resp设置一下编码，使得页面不会出现乱码情况
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        //获取前端传来的数据，这个数据名可以从前面写的jsp文件中找到
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] hobbys = req.getParameterValues("hobbys");
        System.out.println("===================");
        System.out.println(username);
        System.out.println(password);
        System.out.println(Arrays.toString(hobbys));
        System.out.println("===================");

        System.out.println(req.getContextPath());

        //其自动会寻找本项目下的success.jsp，然后进行请求转发
        req.getRequestDispatcher("/error.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入post方法");
        doGet(req, resp);
    }


}
