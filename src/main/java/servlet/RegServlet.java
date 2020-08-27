package servlet;

import model.User;
import store.PsqlStore;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 27.08.2020
 */
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("/registration/reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = PsqlStore.instOf();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm = req.getParameter("copy_password");
        if (validation(name, email, password, confirm)) {
            User result = new User();
            result.setName(name);
            result.setEmail(email);
            result.setPassword(password);
            store.saveUser(result);
        } else {
            req.setAttribute("error", "Пароли не совпадают, или такой пользователь уже существует");
            req.getRequestDispatcher("/registration/reg.jsp").forward(req, resp);
        }
    }

    /**
     * Method execute validation form
     *
     * @param name
     * @param email
     * @param password
     * @param confirm
     * @return boolean
     */
    private boolean validation(String name, String email, String password, String confirm) {
        if (name == null || email == null || password == null || confirm == null) {
            return false;
        }
        if (!password.equals(confirm)) {
            return false;
        }
        if (PsqlStore.instOf().findByEmail(email) != null) {
            return false;
        }
        return true;
    }
}
