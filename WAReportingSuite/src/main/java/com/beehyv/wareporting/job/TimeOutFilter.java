package com.beehyv.wareporting.job;

/**
 * Created by beehyv on 27/11/17.
 */

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.beehyv.wareporting.utils.Global.retrieveUiAddress;

@WebFilter()
public class TimeOutFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("filter called");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpSession session = req.getSession(false);
        if (session != null && !session.isNew()) {
            chain.doFilter(request, response);
        } else {
            System.out.println("Has timed out");
            req.getRequestDispatcher(retrieveUiAddress() +"login").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}