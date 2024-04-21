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

@Order(1)
@Component
public class IdFilter extends OncePerRequestFilter {

    @Override // return true > 不用過 IdFilter
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return FilterParams.NO_FILTER_PATHS.contains(requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id.equals("1")) {
            System.out.println("FirstFilter Success");
            filterChain.doFilter(request, response);
        } else {
            throw new MyException("FirstFilter Error");
        }
    }

}
