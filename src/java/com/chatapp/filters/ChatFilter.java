package com.chatapp.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Account;

@WebFilter(urlPatterns = {"/users/update", "/chat", "/files"})
public class ChatFilter implements Filter {

    public ChatFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Account user = (Account) httpServletRequest.getSession().getAttribute("account");
        if (user == null) {
            // chuyển hướng đến DoGet của /login
            httpServletResponse.sendRedirect("/ManageLodgingHouse/LoginServlet");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

}
