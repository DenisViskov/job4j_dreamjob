package servlet;

import model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import store.MemStore;
import store.PsqlStore;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PsqlStore.class})
public class PostServletTest {

    @Test
    public void doPostTest() throws ServletException, IOException {
        Store store = MemStore.instOf();
        mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("Java jun");
        when(res.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        doNothing().when(res).sendRedirect(anyString());
        new PostServlet().doPost(req, res);
        List<Post> expected = new ArrayList<>(store.findAllPosts());
        assertThat(expected.get(expected.size() - 1).getName(), is("Java jun"));
    }

    @Test
    public void doPostUpdateTest() throws ServletException, IOException {
        Store store = MemStore.instOf();
        Post post = new Post(2, "desc", "desc", null);
        store.save(post);
        mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn(String.valueOf(post.getId()));
        when(req.getParameter("name")).thenReturn("Java jun");
        when(res.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        doNothing().when(res).sendRedirect(anyString());
        new PostServlet().doPost(req, res);
        assertThat(store.findById(post.getId()).getName(), is("Java jun"));
    }
}