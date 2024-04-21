package com.filter;

import com.exception.MyException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@Component
public class FirstFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;
        String id = httpReq.getParameter("id");
        if (id.equals("1")) {
            System.out.println("FirstFilter Success");
            filterChain.doFilter(httpReq, httpResp);
        } else {
            throw new MyException("FirstFilter Error");
        }
    }

}
