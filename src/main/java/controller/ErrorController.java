package controller;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 25/02/2017.
 */
public class ErrorController extends Controller {
    public ErrorController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void notFound(Object exception) {
        renderTemplate("404");
    }
}
