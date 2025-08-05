package com.example;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8082);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(HelloServlet.class, "/");

        server.start();
        server.join();
    }

    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/html");
            resp.getWriter().println("<h1>Hello from Maven Web App!</h1>");
        }
    }
}
