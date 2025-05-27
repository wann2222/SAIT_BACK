package com.ssafy.trip.controller;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerHelper {

    public default String preProcessing(HttpServletRequest req, HttpServletResponse resp) {
        System.out.printf("##요청 경로: %s, 요청 방식: %s##\n", req.getRequestURI(), req.getMethod());
        System.out.println("요청 파라미터 분석");
        req.getParameterMap().forEach((k, v) -> {
            System.out.printf("name: %s, value: %s\n", k, Arrays.toString(v));
        });
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        if (action == null || action.isBlank()) {
            action = "index";
        }
        return action;
    }

    public default void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
    	response.sendRedirect(request.getContextPath() + path); // /는 container root이므로 context root 추가
    }
    
    public default void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
    	RequestDispatcher disp = request.getRequestDispatcher(path);
    	disp.forward(request, response);
    }
    //END
}
