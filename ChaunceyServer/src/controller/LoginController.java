package controller;

import server.HttpServlet;
import server.HttpServletRequest;
import server.HttpServletResponse;

/**
 * @author Chauncey Sun
 * @create 2022/10/18 23:01
 */
public class LoginController extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        System.out.println(name + "---" + password);
    }
}
