package servlet;

import model.Post;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class is a Post servlet
 *
 * @author Денис Висков
 * @version 1.0
 * @since 19.08.2020
 */
public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().save(new Post(0, req.getParameter("name"), null, LocalDateTime.now()));
        resp.sendRedirect(req.getContextPath() + "/post/posts.jsp");
    }
}
