package servlet;

import model.Candidate;
import store.PsqlStore;
import store.Store;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Class servlet Delete candidate
 *
 * @author Денис Висков
 * @version 1.0
 * @since 25.08.2020
 */
public class DeleteCandidate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = PsqlStore.instOf();
        Candidate candidate = store.findByIdCandidate(Integer.valueOf(req.getParameter("id")));
        String tomcatHome = System.getenv("CATALINA_HOME");
        File file = new File(tomcatHome
                + File.separator
                + "bin"
                + File.separator
                + "images"
                + File.separator
                + candidate.getPhoto());
        file.delete();
        store.deleteCandidate(Integer.valueOf(req.getParameter("id")));
        req.setAttribute("candidates", store.findAllCandidates());
        req.getRequestDispatcher("candidate/candidates.jsp").forward(req, resp);
    }
}
