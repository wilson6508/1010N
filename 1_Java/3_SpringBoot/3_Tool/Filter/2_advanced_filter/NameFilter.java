package com.filter;

import com.exception.MyException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(2)
@Component
public class NameFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name.equals("Tom")) {
            System.out.println("SecondFilter Success");
            filterChain.doFilter(request, response);
        } else {
            throw new MyException("SecondFilter Error");
        }
    }

}
