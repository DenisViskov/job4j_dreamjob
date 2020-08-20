package servlet;

import model.Candidate;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class is a Candidate servlet
 *
 * @author Денис Висков
 * @version 1.0
 * @since 19.08.2020
 */
public class CandidateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().saveCandidate(
                new Candidate(Integer.valueOf(req.getParameter("id")),
                        req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.jsp");
    }
}
