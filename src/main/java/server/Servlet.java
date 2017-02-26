package server;

import hyggemvc.controller.Controller;
import hyggemvc.router.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam on 25/02/2017.
 */
public class Servlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        route(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        route(request, response);
    }

    private void route(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = ((String) request.getAttribute("origin"));
        Router router = new BasicRouter();
        Route route = new BasicRoute("controller");
        router.inflateRoute(route, uri);
        RouteCaller routeCaller = new RouteCaller(route, request, response);
        Controller controller = routeCaller.callRoute();
    }
}
