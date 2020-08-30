package servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import store.MemStore;
import store.PsqlStore;
import store.Store;
import sun.misc.Unsafe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PsqlStore.class})
public class RegServletTest {

    @Test
    public void doPostRegTest() throws ServletException, IOException {
        Store store = MemStore.instOf();
        mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("name");
        when(req.getParameter("email")).thenReturn("email@mail.ru");
        when(req.getParameter("password")).thenReturn("password");
        when(req.getParameter("copy_password")).thenReturn("password");
        RegServlet servlet = new RegServlet();
        servlet.doPost(req,res);
        assertNotNull(store.findByEmail("email@mail.ru"));
    }
}