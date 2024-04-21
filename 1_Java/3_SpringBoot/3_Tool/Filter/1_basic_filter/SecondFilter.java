package com.filter;

import com.exception.MyException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(2)
@Component
public class SecondFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;
        String name = httpReq.getParameter("name");
        if (name.equals("Tom")) {
            System.out.println("SecondFilter Success");
            filterChain.doFilter(httpReq, httpResp);
        } else {
            throw new MyException("SecondFilter Error");
        }
    }

}
