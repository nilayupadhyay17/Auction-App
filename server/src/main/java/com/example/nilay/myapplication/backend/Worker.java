package com.example.nilay.myapplication.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by nilay on 2/9/2017.
 */

public class Worker extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        resp.setContentType("text/plain");
        HttpSession session = req.getSession();
        long seconds = System.currentTimeMillis();
        Date date = new Date(seconds);
        PrintWriter writer = resp.getWriter();
        System.out.println("Last accessed time: " + date);
        resp.getWriter().println("Seconds: " + seconds);
        //String key = req.getParameter("key");

       // resp.getWriter().println("hello"+key);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
    }
    public void contextInitialized(ServletContextEvent event) {

    }

}
