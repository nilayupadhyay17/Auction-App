/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.nilay.myapplication.backend;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet{
    HttpServletResponse httpServletResponse;
    String name;
    PrintWriter out;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
        httpServletResponse = resp;

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        name = name;
        Queue queue = QueueFactory.getDefaultQueue();
        resp.setContentType("text/plain");
        //queue.add(TaskOptions.Builder.withUrl("/tasks/myWorker").param("key","Nilay"));
        //RequestDispatcher rd=req.getRequestDispatcher("/tasks/myWorker");
        /*try {
            rd.include(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }*/
        //resp.sendRedirect("/tasks/myWorker");
        out = resp.getWriter();
       // startTimer();
        String name = req.getParameter("name");

        if (name == null) {
           // resp.getWriter().println("Please enter a name");
        }
        //resp.getWriter().println("Hello " + name);
    }

    private void startTimer() {


        // This works

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                    final long start = System.currentTimeMillis();
                    final long end = System.currentTimeMillis();
                    final long timeSpent = end - start;
                    if (timeSpent>2000){
                        out.print(timeSpent);
                    }
            }

        });

        thread.start();

        System.out.print("hii");
        /*Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    httpServletResponse.getWriter().println("Hello " + name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000,1000);
    }*/
    }


}
