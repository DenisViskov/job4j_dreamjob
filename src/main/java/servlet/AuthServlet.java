package servlet;

import model.User;
import store.PsqlStore;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class is an Authorization servlet
 *
 * @author Денис Висков
 * @version 1.0
 * @since 27.08.2020
 */
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = PsqlStore.instOf().findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.getOutputStream().println("Welcome");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("/login/login.jsp").forward(req, resp);
        }
    }
}

