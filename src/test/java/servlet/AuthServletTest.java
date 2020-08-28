package servlet;

import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import store.MemStore;
import store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class AuthServletTest {

    @Test
    public void whenWeHaveUserTest() throws IOException, ServletException {
        MemStore store = MemStore.instOf();
        store.saveUser(new User(0, "Name", "asd@mail.ru", "password"));
        mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        when(req.getParameter("email")).thenReturn("asd@mail.ru");
        when(req.getParameter("password")).thenReturn("password");
        when(req.getSession()).thenReturn(mock(HttpSession.class));
        AuthServlet servlet = new AuthServlet();
        servlet.doPost(req, res);
        Mockito.verify(res).sendRedirect(anyString());
    }

    @Test
    public void whenWeDontHaveUserTest() throws IOException, ServletException {
        MemStore store = MemStore.instOf();
        mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        when(req.getParameter("email")).thenReturn("a@mail.ru");
        when(req.getParameter("password")).thenReturn("password");
        when(req.getRequestDispatcher(anyString())).thenReturn(mock(RequestDispatcher.class));
        AuthServlet servlet = new AuthServlet();
        servlet.doPost(req, res);
        Mockito.verify(req).getRequestDispatcher(anyString());
    }
}