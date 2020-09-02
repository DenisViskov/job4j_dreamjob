package servlet;

import model.Post;
import org.json.JSONObject;
import store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class is a Post servlet
 *
 * @author Денис Висков
 * @version 1.0
 * @since 19.08.2020
 */
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", new ArrayList<>(PsqlStore.instOf().findAllPosts()));
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("/post/posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        PsqlStore.instOf().save(
                new Post(
                        Integer.valueOf(req.getParameter("id")),
                        req.getParameter("name"),
                        null,
                        LocalDateTime.now()
                )
        );
        JSONObject json = new JSONObject();
        json.put("answer", "post was been added");
        writer.println(json);
        writer.flush();
    }
}
