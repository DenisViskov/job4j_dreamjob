package servlet;

import model.Candidate;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import store.PsqlStore;
import store.Store;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Class is a Candidate servlet
 *
 * @author Денис Висков
 * @version 1.0
 * @since 19.08.2020
 */
public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("request") != null) {
            responseToEditJSP(resp);
            return;
        }
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("candidate/candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> value = extractValues(req);
        PsqlStore.instOf().saveCandidate(
                new Candidate(Integer.valueOf(value.get("id")),
                        value.get("name"),
                        value.get("file"),
                        value.get("city")));
        resp.getOutputStream().println("Candidate was been added");
    }

    /**
     * Method of import values from request
     *
     * @param req
     * @return Map of <>id</>
     * <>name</>
     * <>file</>
     * @throws IOException
     */
    private Map<String, String> extractValues(HttpServletRequest req) throws IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        String name = "";
        String city = "";
        File file = null;
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    file = new File(folder + File.separator + item.getName());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
                if ("name".equals(item.getFieldName())) {
                    name = item.getString();
                }
                if ("city".equals(item.getFieldName())) {
                    city = item.getString();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return Map.of("id", req.getParameter("id"),
                "name", name,
                "file", file.getName(),
                "city", city);
    }

    /**
     * Method send response to edit JSP
     *
     * @param resp
     * @throws IOException
     */
    private void responseToEditJSP(HttpServletResponse resp) throws IOException {
        resp.setContentType("json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<String> cities = new ArrayList<>(PsqlStore.instOf().getCities());
        JSONObject json = new JSONObject();
        for (int i = 0; i < cities.size(); i++) {
            json.put(String.valueOf(i), cities.get(i));
        }
        writer.println(json);
        writer.flush();
    }
}
