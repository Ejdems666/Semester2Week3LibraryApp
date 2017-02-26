package controller;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 26/02/2017.
 */
public class SignController extends Controller{
    public SignController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void in(){
        renderTemplate("sign/in");
    }

    public void up(){
        renderTemplate("sign/up");
    }
}
